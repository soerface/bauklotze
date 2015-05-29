#!/usr/bin/env python

import sys
from board import Board
from block import Block

INPUTS = (
    ((1, 3), 1),
    ((6, 1), 1),
    ((2, 3), 3),
    ((3, 3), 10),
    ((6, 2), 11),
    ((3, 4), 23),
    ((9, 2), 41),
    ((3, 5), 62),
    ((6, 4), 939),
    ((9, 3), 3127),
    ((6, 5), 8342),
    ((9, 4), 41813),
    ((6, 6), 80092),
    ((7, 6), 614581),
    ((9, 5), 1269900),
    ((18, 3), 20279829),
    ((19, 3), 53794224),
    ((42, 2), 80198051),
    ((45, 2), 299303201),
)

if __name__ == '__main__':
    blocks = (
        Block(
            (1, 0, 0),
            (1, 0, 0),
            (1, 0, 0),
        ),
        Block(
            (2, 2, 2),
            (0, 0, 0),
            (0, 0, 0),
        ),
        Block(
            (3, 3, 0),
            (3, 0, 0),
            (0, 0, 0),
        ),
        Block(
            (4, 4, 0),
            (0, 4, 0),
            (0, 0, 0),
        ),
        Block(
            (0, 5, 0),
            (5, 5, 0),
            (0, 0, 0),
        ),
        Block(
            (6, 0, 0),
            (6, 6, 0),
            (0, 0, 0),
        ),
    )
    if len(sys.argv) > 1:
        board = Board(int(sys.argv[1]), int(sys.argv[2]), blocks=blocks)
        print board.calculate_mutations()
    else:
        for inp in INPUTS:
            board = Board(*inp[0], blocks=blocks)
            res = board.calculate_mutations()
            print '{0} {1} {res}'.format(*inp[0], res=res)