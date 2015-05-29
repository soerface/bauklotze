import numpy

class Board(object):

    def __init__(self, height, width, blocks):
        if (height * width) % 3 != 0:
            raise ValueError('Field size must be a multiple of 3')
        self.data = numpy.zeros((height, width))
        self.blocks = blocks

    def calculate_mutations(self):
        self.next_positions((0, 0))

    def next_positions(self, *positions):
        for block in self.blocks:
            valid_offsets = set()
            for pos in positions:
                valid_offsets = valid_offsets.union(self.find_valid_offsets(block, pos))
            for offset in valid_offsets:
                self.place_block_at(block, offset)
                next_pos = self.find_next_positions(block, offset)
                print 'next pos:', next_pos
                print self.data

    def find_valid_offsets(self, block, pos):
        valid_offsets = set()
        for coordinate in block.coordinates:
            offset = (
                pos[0] - coordinate[0],
                pos[1] - coordinate[1],
            )
            if self.block_placeable_at(block, offset):
                valid_offsets.add(offset)
        return valid_offsets

        # self.data[i + pos[0]][j + pos[1]] = block.data[i][j]
        # print self.data
        # print self.data[pos[0]][pos[1]]

    def block_placeable_at(self, block, offset):
        if min(offset) < 0:
            return False
        for i in range(len(block.data)):
            for j in range(len(block.data[i])):
                try:
                    if block.data[i][j] != 0 and self.data[i + offset[0]][j + offset[1]] != 0:
                        return False
                except IndexError:
                    return False
        return True

    def place_block_at(self, block, offset):
        for i in range(len(block.data)):
            for j in range(len(block.data[i])):
                if block.data[i][j] != 0:
                    self.data[i + offset[0]][j + offset[1]] = block.data[i][j]

    def find_next_positions(self, block, offset):
        next_positions = set()
        for i in range(len(block.data)):
            for j in range(len(block.data[i])):
                if block.data[i][j] != 0:
                    a = i + offset[0]
                    b = j + offset[1]
                    # above
                    a -= 1
                    if a >= 0 and self.data[a][b] == 0:
                        next_positions.add((a, b))
                    a += 1
                    # below
                    a += 1
                    if a < len(self.data) and self.data[a][b] == 0:
                        next_positions.add((a, b))
                    a -= 1
                    # left
                    b -= 1
                    if b >= 0 and self.data[a][b] == 0:
                        next_positions.add((a, b))
                    b += 1
                    # right
                    b += 1
                    if b < len(self.data[a]) and self.data[a][b] == 0:
                        next_positions.add((a, b))
                    b -= 1
        return next_positions