import numpy


class Block(object):

    def __init__(self, row1, row2, row3):
        self.data = numpy.array([row1, row2, row3])
        self.coordinates = numpy.zeros((3, 2))
        k = 0
        for i in range(len(self.data)):
            for j in range(len(self.data[i])):
                if self.data[i][j]:
                    self.coordinates[k] = (i, j)
                    k += 1