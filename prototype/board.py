import numpy

class Board(object):

    def __init__(self, height, width):
        self.data = numpy.zeros((height, width))

    def place_block(self, block, pos):
        valid_offsets = set()
        for coordinate in block.coordinates:
            offset = (
                pos[0] - coordinate[0],
                pos[1] - coordinate[1],
            )
            if self.block_placable_at(block, offset):
                valid_offsets.add(offset)
        print block.data
        print valid_offsets

        # self.data[i + pos[0]][j + pos[1]] = block.data[i][j]
        # print self.data
        # print self.data[pos[0]][pos[1]]

    def block_placable_at(self, block, offset):
        if min(offset) < 0:
            return False
        for i in range(len(block.data)):
            for j in range(len(block.data[i])):
                try:
                    if block.data[i][j] and self.data[i + offset[0]][j + offset[1]]:
                        return False
                except IndexError, e:
                    return False
        return True