#!/usr/bin/env python

from board import Board
from block import Block

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
    board = Board(3, 2, blocks)
    print board.data
    print board.calculate_mutations()