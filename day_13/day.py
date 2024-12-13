from collections import defaultdict
import pprint

from day_13.Models import Machine

PRINT_ENABLED = True
file_name = 'example.txt'
# file_name = 'input.txt'

data = []

def solve_ecuation(xt, yt, ax, ay, bx, by):
    # TODO: add formula
    var_b = (ay * xt) / ax
    var2_b = (ay * bx) / ax
    combined = by - var2_b

    b = (yt - var_b) / combined
    a = (xt - bx * b) / ax

    # if PRINT_ENABLED:
        # print("a=", int(a), "b=", int(b))

    return (round(a, 2), round(b, 2))

def create_machine(data):
    button_a_line = data[0]
    button_b_line = data[1]
    prize_line = data[2]

    ax = int(button_a_line.split(': ')[1].split(', ')[0][2:])
    ay = int(button_a_line.split(', ')[1][2:])

    bx = int(button_b_line.split(': ')[1].split(', ')[0][2:])
    by = int(button_b_line.split(', ')[1][2:])

    xt = int(prize_line.split(': ')[1].split(', ')[0][2:])
    yt = int(prize_line.split(', ')[1][2:])

    return Machine(ax, ay, bx, by, xt, yt)

def can_be_int(number):
    if isinstance(number, int):
        return True
    elif isinstance(number, float):
        if number.is_integer():
            return True
        else:
            return False
    else:
        return False

with open(file_name) as f:
    current_data = []
    for line in f.read().strip().split('\n'):
        line = line.strip()
        if not line:
            if current_data:
                data.append(create_machine(current_data))
                current_data = []
        else:
            current_data.append(line)

    if current_data:
        data.append(create_machine(current_data))


if PRINT_ENABLED:
    pprint.pprint(data)

for m in data:
    (a, b) = solve_ecuation(m.xt, m.yt, m.buttons[0].dx, m.buttons[0].dy,
                            m.buttons[1].dx, m.buttons[1].dy)
    if can_be_int(a) and can_be_int(b):
        m.tokens = int(3 * a + b)
        print(a, b, m.tokens)


res = sum([ m.tokens for m in data if m.tokens is not ( None or 0 ) ])
print(res)


# solve_ecuation(8400, 5400, 94, 34 , 22 , 67)



