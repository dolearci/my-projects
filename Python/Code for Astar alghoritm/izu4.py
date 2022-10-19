#!/usr/bin/env python3
from copy import deepcopy
import io, re


def points_distance(x, y):
    """calculates Euclidean distance between two points in n-dimensional space"""
    tmp = 0
    for x, y in zip(x, y):
        tmp += (y - x) ** 2
    return tmp ** (1 / 2)  # sqrt without library


def calculate_center(points):
    """calculates center (Euclidean) between n points in n-dimensional space
    all points must have same dimension!"""
    new_center = [0] * len(points[0])
    for index, x in enumerate(zip(*points)):
        new_center[index] = sum(x) / len(x)
    return new_center


def mround(match):
    """https://stackoverflow.com/questions/7584113/rounding-using-regular-expressions"""
    return "{:.3f}".format(float(match.group()))


centers = [[-3,4,1],[-4,-1,-3],[0,-1,-6]] #[[-2, 2, -4], [-1, -1, 1], [4, -1, 4]]
points = [
    [0, -1, -2],
    [-3, -1, -3],
    [1, -3, 2],
    [-2, -2, 2],
    [1, 2, -4],
    [0, -4, 3],
    [1, 0, -3],
    [-3, 0, 0],
    [-2, 2, -4],
    [-2, 4, 3],
    [3, -2, 4],
    [2, -5, -4],
]
backup = deepcopy(centers)
iteration = 1
output = io.StringIO()
output_print = lambda *x, **y: print(*x, **y, file=output)
while True:
    output_print("\n\n", "=" * 15, "\n Iterácia:", iteration, "\n", "=" * 15)
    iteration += 1
    clusters = [[] for x in centers]
    output_print("Ťažisko:", centers)
    output_print(
        "Vzdialenosť bodov od ťažiska v tvare ([bod]--> [těžiště] = vzdálenost | [těžiště] = vzdálenost | [těžiště] = vzdálenost):"
    )
    for x in points:
        distances = []
        for y in centers:
            distances.append(points_distance(x, y))
        
        clusters[distances.index(min(distances))].append(x)
        output_print(x, " " * (12 - len(str(x))), "--> ", end="")
        for index, distance in enumerate(distances):
            output_print(centers[index], "=", distance,"| ", end="")
        output_print()
        

    for index, x in enumerate(clusters):
        output_print("zhluk", index + 1, ":", x)

    for index, x in enumerate(centers):
        centers[index] = calculate_center(clusters[index])

    if backup == centers:
        break

    backup = deepcopy(centers)

output_print("\n", "=" * 15, "\n Výsledky:", "\n", "=" * 15)
output_print("Výsledné ťažiská: ", centers)
for index, x in enumerate(clusters):
    output_print("zhluk", index + 1, ":", x)

output.flush()
output.seek(0)
simpledec = re.compile(r"\d*\.\d+")
print(re.sub(simpledec, mround, output.read()))
