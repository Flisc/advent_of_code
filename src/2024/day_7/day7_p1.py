# file_name = 'example.txt'
file_name = 'puzzle.txt'
res = 0


def apply_operations(numbers, operations):
    result = numbers[0]
    for i in range(len(operations)):
        if operations[i] == '+':
            result += numbers[i + 1]
        else:
            result *= numbers[i + 1]
    print(f"numbers: {numbers},\noperati: {operations} result: {result}")
    return result


def equation_possible(value, numbers):
    if len(numbers) == 1:
        return value == numbers[0]

    def try_(position, operations):
        if position == len(numbers) - 1:
            return apply_operations(numbers, operations) == value

        return (try_(position + 1, operations + ['+'])
                or try_(position + 1, operations + ['*']))

    return try_(0, [])




with open(file_name) as f:
    for line in f:
        test_val, numbers = line.split(": ")
        test_val = int(test_val)
        numbers = [int(i) for i in numbers.split()]
        if equation_possible(test_val, numbers):
            res += test_val
        # print(test_val)
        # print(numbers)

print(res)
