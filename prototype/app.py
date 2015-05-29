#!/usr/bin/env python

from board import Board
from block import Block

if __name__ == '__main__':
    blocks = (
        Block(
            (1, 0, 0),
            (1, 0, 0),
            (1, 0, 0),
            1
        ),
        Block(
            (1, 1, 1),
            (0, 0, 0),
            (0, 0, 0),
            2
        ),
        Block(
            (1, 1, 0),
            (1, 0, 0),
            (0, 0, 0),
            3
        ),
        Block(
            (1, 1, 0),
            (0, 1, 0),
            (0, 0, 0),
            4
        ),
        Block(
            (0, 1, 0),
            (1, 1, 0),
            (0, 0, 0),
            5
        ),
        Block(
            (1, 0, 0),
            (1, 1, 0),
            (0, 0, 0),
            6
        ),
    )
    board = Board(3, 1, blocks)
    print board.data
    print board.calculate_mutations()