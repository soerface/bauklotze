#!/usr/bin/env python

from board import Board
from block import Block

if __name__ == '__main__':
    board = Board(3, 1)
    print board.data
    blocks = (
        Block(
            (1, 0, 0),
            (1, 0, 0),
            (1, 0, 0),
        ),
        Block(
            (1, 1, 1),
            (0, 0, 0),
            (0, 0, 0),
        ),
        Block(
            (1, 1, 0),
            (1, 0, 0),
            (0, 0, 0),
        ),
        Block(
            (1, 1, 0),
            (0, 1, 0),
            (0, 0, 0),
        ),
        Block(
            (0, 1, 0),
            (1, 1, 0),
            (0, 0, 0),
        ),
        Block(
            (1, 0, 0),
            (1, 1, 0),
            (0, 0, 0),
        ),
    )
    for block in blocks:
        board.place_block(block, (0, 0))