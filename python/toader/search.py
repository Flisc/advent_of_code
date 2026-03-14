#!/usr/bin/env python3
"""
Search orders by product name in a JSON structure.
Usage: python search_orders.py <data_file.json> <product_name>
       python search_orders.py <data_file.json> <product_name> --show-deleted
"""

import json
import os
import sys
from datetime import datetime, timezone


def get_product_names(item: dict) -> list[str]:
    """Extract all product name variants from an order item."""
    names = []
    item_data = item.get("item", {})

    # item.item.productName
    if pn := item_data.get("productName"):
        names.append(pn)

    # item.item.product.label
    if label := item_data.get("product", {}).get("label"):
        names.append(label)

    return names


def format_timestamp(ms: int) -> str:
    """Convert millisecond timestamp to human-readable date."""
    try:
        dt = datetime.fromtimestamp(ms / 1000, tz=timezone.utc).astimezone()
        return dt.strftime("%Y-%m-%d %H:%M:%S")
    except Exception:
        return str(ms)


def search_orders(data: dict, search_term: str, show_deleted: bool = False) -> list[dict]:
    """Find all orders containing a product matching the search term (case-insensitive)."""
    search_lower = search_term.strip().lower()
    results = []

    for order_id, order in data.items():
        # Skip deleted orders unless explicitly requested
        if not show_deleted and order.get("deletedAt"):
            continue

        comanda = order.get("comanda", {})
        items = comanda.get("items", [])

        matched_products = []

        for item in items:
            for name in get_product_names(item):
                if search_lower in name.lower():
                    product_label = item.get("item", {}).get("product", {}).get("label", name)
                    matched_products.append({
                        "productName": name,
                        "label": product_label,
                        "bucati": item.get("bucati"),
                        "stoc": item.get("item").get("bucati"),
                        "mc": item.get("mc"),
                        "pret": item.get("pret"),
                    })
                    break  # avoid duplicate matches for the same item

        if matched_products:
            results.append({
                "order_id": order_id,
                "data": order.get("data", 0),
                "data_str": format_timestamp(order.get("data", 0)),
                "clientName": comanda.get("clientName", "—").strip(),
                "tip": order.get("tip", "—"),
                "total_comanda": order.get("total_comanda"),
                "livrat": order.get("livrat"),
                "incasat": order.get("incasat"),
                "deleted": bool(order.get("deletedAt")),
                "matched_products": matched_products,
                "full_order": order,
            })

    # Sort by date ascending
    results.sort(key=lambda x: x["data"])
    return results


def print_results(results: list[dict], search_term: str):
    """Display results in a user-friendly table."""
    if not results:
        print(f"\n  No orders found containing product: '{search_term}'\n")
        return

    print(f"\n{'═' * 80}")
    print(f"  Orders containing product: '{search_term}'  ({len(results)} found)")
    print(f"{'═' * 80}\n")

    for i, r in enumerate(results, 1):
        deleted_tag = "  ⚠ Stoc: " if r["tip"] == 'RETUR_COMANDA' 'INTRARE' else "IESIRE"
        print(f"  [{i}] {r['data_str']}{deleted_tag}")
        print(f"      Client   : {r['clientName']}")
        print(f"      Tip      : {r['tip']}")
        print(f"      Total    : {r['total_comanda']} RON")
        # print(f"      Livrat   : {'✓' if r['livrat'] else '✗'}   "
        #       f"Incasat: {'✓' if r['incasat'] else '✗'}")
        print(f"      Products :")
        for p in r["matched_products"]:
            print(f"               • {p['label']}  "
                  f"| {p['bucati']} buc  "
                  f"| {p['mc']} mc  "
                  f"| {p['pret']} RON")
            print(f"\t\t\t\t Stoc: {p['stoc']} BUC")
        print(f"      Order ID : {r['order_id']}")
        print()

    print(f"{'═' * 80}\n")


def main():
    BASE_DIR = os.path.dirname(os.path.abspath(__file__))
    DATA_FILE = os.path.join(BASE_DIR, "tirtoader_deleted_2026_MAR-export.json")
    PRODUCT = "Scandura (S20x4m)"

    # Load JSON
    try:
        with open(DATA_FILE, "r", encoding="utf-8") as f:
            data = json.load(f)
    except FileNotFoundError:
        print(f"Error: File '{args.file}' not found.")
        sys.exit(1)
    except json.JSONDecodeError as e:
        print(f"Error: Invalid JSON — {e}")
        sys.exit(1)

    results = search_orders(data, PRODUCT, show_deleted=True)
    print_results(results, PRODUCT)


if __name__ == "__main__":
    main()