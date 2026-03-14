#!/usr/bin/env python3
"""
Search orders and history by product name, showing chronological stock movements.

File auto-detection:
  - ORDER file   : entries WITHOUT a "tip" field  → always stock OUT (-)
  - HISTORY file : entries WITH a "tip" field     → direction depends on tip:
      RETUR COMANDA      → stock IN  (+)
      MODIFICARE COMANDA → neutral   (~)  (edit/correction, no stock change)
      anything else      → stock OUT (-)

Usage:
  python search_orders.py <product_name>
  python search_orders.py <product_name> --show-deleted
  python search_orders.py <product_name> --orders-file my_orders.json --history-file my_history.json

Place orders.json and history.json in the same directory as this script,
or override paths with --orders-file / --history-file.
"""

import argparse
import json
import os
from datetime import datetime, timezone

# ── Default file paths (same directory as script) ───────────────────────────
BASE_DIR             = os.path.dirname(os.path.abspath(__file__))
DEFAULT_ORDERS_FILE  = os.path.join(BASE_DIR, "tirtoader_orders_2026_FEB-export.json")
DEFAULT_HISTORY_FILE = os.path.join(BASE_DIR, "tirtoader_deleted_2026_MAR-export.json")
DEFAULT_PRODUCT      = "Scandura (S20x4m)" # product name to search
DEFAULT_FROM_DATE    = "2026-02-26"        # start date
DEFAULT_SHOW_DELETED = True                # include soft-deleted entries

# ── Stock-direction logic ────────────────────────────────────────────────────
# tip value (lowercase) → (direction_label, sign)
TIP_DIRECTION = {
    "retur comanda":      ("RETUR",      "+"),   # stock comes back
    "modificare comanda": ("MODIFICARE", "~"),   # neutral edit
}
DEFAULT_HISTORY_DIRECTION = ("VANZARE", "-")     # any other tip = sale out


def stock_direction(tip):
    """Return (label, sign) for a given tip value. None means it's a plain order."""
    if tip is None:
        return ("COMANDA", "-")
    return TIP_DIRECTION.get(tip.strip().lower(), DEFAULT_HISTORY_DIRECTION)


# ── Helpers ──────────────────────────────────────────────────────────────────
def get_product_names(item):
    """Extract all product name variants from an order item."""
    names = []
    item_data = item.get("item", {})
    if pn := item_data.get("productName"):
        names.append(pn)
    if label := item_data.get("product", {}).get("label"):
        names.append(label)
    return names


def format_ts(ms):
    try:
        dt = datetime.fromtimestamp(ms / 1000, tz=timezone.utc).astimezone()
        return dt.strftime("%Y-%m-%d %H:%M:%S")
    except Exception:
        return str(ms)


def load_json(path):
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


# ── Core search ──────────────────────────────────────────────────────────────
def extract_entries(data, search_lower, source_label, show_deleted):
    """
    Parse one file (orders or history) and return matching entries.
    source_label: short string shown in output ("ORDER" / "HISTORY")
    """
    entries = []

    for entry_id, entry in data.items():
        if not show_deleted and entry.get("deletedAt"):
            continue

        comanda = entry.get("comanda", {})
        items   = comanda.get("items", [])
        tip     = entry.get("tip")          # None for plain orders

        direction_label, sign = stock_direction(tip)

        matched_products = []
        for item in items:
            for name in get_product_names(item):
                if search_lower in name.lower():
                    label = item.get("item", {}).get("product", {}).get("label", name)
                    matched_products.append({
                        "productName": name,
                        "label":       label,
                        "bucati":      item.get("bucati"),
                        "mc":          item.get("mc"),
                        "pret":        item.get("pret"),
                        "stoc_initial": item.get("item").get("bucati"),
                    })
                    break  # one match per item is enough

        if not matched_products:
            continue

        entries.append({
            "entry_id":         entry_id,
            "source":           source_label,
            "data":             entry.get("data", 0),
            "data_str":         format_ts(entry.get("data", 0)),
            "clientName":       comanda.get("clientName", "—").strip() or "—",
            "tip":              tip or "—",
            "direction_label":  direction_label,
            "sign":             sign,
            "total_comanda":    entry.get("total_comanda"),
            "livrat":           entry.get("livrat"),
            "incasat":          entry.get("incasat"),
            "stoc_initial":     entry.get("stoc_initial"),
            "stoc_dupa":        entry.get("stoc_dupa_vanzare"),
            "deleted":          bool(entry.get("deletedAt")),
            "matched_products": matched_products,
        })

    return entries


def parse_from_date(date_str):
    """Parse a date string (YYYY-MM-DD or DD.MM.YYYY) into a millisecond timestamp."""
    for fmt in ("%Y-%m-%d", "%d.%m.%Y"):
        try:
            dt = datetime.strptime(date_str, fmt).replace(
                hour=0, minute=0, second=0, tzinfo=timezone.utc
            )
            return int(dt.timestamp() * 1000)
        except ValueError:
            continue
    raise argparse.ArgumentTypeError(
        f"Invalid date '{date_str}'. Use YYYY-MM-DD or DD.MM.YYYY"
    )


def search_all(orders_file, history_file, search_term, show_deleted, from_ts=None):
    results = []

    for path, label in [(orders_file, "ORDER"), (history_file, "HISTORY")]:
        if not path:
            continue
        if not os.path.exists(path):
            print(f"  [skip] File not found: {path}")
            continue
        try:
            data = load_json(path)
        except json.JSONDecodeError as e:
            print(f"  [skip] Invalid JSON in {path}: {e}")
            continue

        found = extract_entries(data, search_term.strip().lower(), label, show_deleted)
        results.extend(found)

    results.sort(key=lambda x: x["data"])

    if from_ts is not None:
        results = [r for r in results if r["data"] >= from_ts]

    return results


# ── Display ──────────────────────────────────────────────────────────────────
SIGN_COLOR = {"+": "\033[32m", "-": "\033[31m", "~": "\033[33m"}
RESET      = "\033[0m"
BOLD       = "\033[1m"


def colorize(sign, text):
    return f"{SIGN_COLOR.get(sign, '')}{text}{RESET}"


def print_results(results, search_term, from_date_str=None):
    if not results:
        from_info = f" from {from_date_str}" if from_date_str else ""
        print(f"\n  No entries found for product: '{search_term}'{from_info}\n")
        return

    from_info = f"  from {from_date_str}" if from_date_str else ""
    print(f"\n{BOLD}{'═' * 82}{RESET}")
    print(f"{BOLD}  Chronological stock movements for: '{search_term}'{from_info}  "
          f"({len(results)} entries){RESET}")
    print(f"{BOLD}{'═' * 82}{RESET}\n")

    total_mc_in  = 0.0
    total_mc_out = 0.0

    for i, r in enumerate(results, 1):
        sign    = r["sign"]
        deleted = "  ⚠ DELETED" if r["deleted"] else ""
        src_tag = f"[{r['source']}]"

        header = (f"  {colorize(sign, f'[{i}]')}  "
                  f"{BOLD}{r['data_str']}{RESET}  "
                  f"{colorize(sign, sign + ' ' + r['direction_label'])}  "
                  f"{src_tag}{deleted}")
        print(header)
        print(f"       Client  : {r['clientName']}")
        print(f"       Tip     : {r['tip']}")

        for p in r["matched_products"]:
            mc_val = p["mc"] or 0.0
            if sign == "+":
                total_mc_in  += mc_val
            elif sign == "-":
                total_mc_out += mc_val

            mc_str = colorize(sign, f"{sign}{mc_val:.3f} mc")
            print(f"       Product : {p['label']}"
                  f"  | {p['bucati']} buc  | {mc_str}  | {p['pret']} RON")
            print(f"      \t\t\tStoc : {p['stoc_initial']} BUC")

        stoc_info = ""
        if r["stoc_initial"] is not None and r["stoc_dupa"] is not None:
            stoc_info = (f"  stoc: {r['stoc_initial']:.3f} → "
                         f"{r['stoc_dupa']:.3f} mc")
        print(f"       Total   : {r['total_comanda']} RON{stoc_info}")
        print()

    # Summary
    net = total_mc_in - total_mc_out
    net_str = colorize("+" if net >= 0 else "-", f"{net:+.3f} mc")
    print(f"{'─' * 82}")
    print(f"  Summary   IN : {colorize('+', f'+{total_mc_in:.3f} mc')}   "
          f"OUT: {colorize('-', f'-{total_mc_out:.3f} mc')}   "
          f"NET: {net_str}")
    print(f"{'═' * 82}\n")


# ── Entry point ──────────────────────────────────────────────────────────────
def main():
    parser = argparse.ArgumentParser(
        description="Search orders & history by product, sorted chronologically.",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Stock direction:
  ORDER file (no 'tip')          →  - OUT  (sale / delivery)
  HISTORY tip=RETUR COMANDA      →  + IN   (stock return)
  HISTORY tip=MODIFICARE COMANDA →  ~ NEUTRAL (edit, no stock change)
  HISTORY any other tip          →  - OUT

Examples:
  python search_orders.py Coarne
  python search_orders.py "Mini Coarne" --show-deleted
  python search_orders.py Leturi --orders-file data/orders.json --history-file data/hist.json
        """
    )
    # parser.add_argument("product",
    #                     help="Product name to search (partial, case-insensitive)")
    parser.add_argument("product", nargs="?", default=DEFAULT_PRODUCT,
                        help=f"Product name to search, partial/case-insensitive "
                             f"(default: '{DEFAULT_PRODUCT}')")
    parser.add_argument("--show-deleted", action="store_true",
                        default=DEFAULT_SHOW_DELETED,
                        help=f"Include soft-deleted entries "
                             f"(default: {DEFAULT_SHOW_DELETED})")
    parser.add_argument("--from-date", default=DEFAULT_FROM_DATE,
                        type=parse_from_date, metavar="DATE",
                        help=f"Only show entries on or after this date. "
                             f"Formats: YYYY-MM-DD or DD.MM.YYYY "
                             f"(default: {DEFAULT_FROM_DATE})")
    parser.add_argument("--orders-file", default=DEFAULT_ORDERS_FILE,
                        help=f"Path to orders JSON  (default: {DEFAULT_ORDERS_FILE})")
    parser.add_argument("--history-file", default=DEFAULT_HISTORY_FILE,
                        help=f"Path to history JSON (default: {DEFAULT_HISTORY_FILE})")

    args = parser.parse_args()

    results = search_all(
        orders_file=args.orders_file,
        history_file=args.history_file,
        search_term=args.product,
        show_deleted=args.show_deleted,
        from_ts=args.from_date,
    )
    from_date_str = args.from_date and datetime.fromtimestamp(
        args.from_date / 1000, tz=timezone.utc
    ).strftime("%Y-%m-%d")

    print_results(results, args.product, from_date_str=from_date_str)


if __name__ == "__main__":
    main()
