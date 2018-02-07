# test

""" One object of class Rectangle stores a rectangle's length and width.
Modify the setData() method so that it will set the value of the height ONLY if only the width parameter is negative,
and it will set the value of the width ONLY if only the height parameter is negative.
If either or both parameters are negative, it will raise a ValueError.
Make sure to test this thoroughly, as it is not as easy to code as you might expect.
"""


class Rectangle:
    popln = 0

    def __init__(self):
        self.height = 0
        self.width = 0

    def setData(self, newHeight, newWidth):
        if newHeight >= 0 and newWidth >= 0:  # both are positive
            self.height = newHeight
            self.width = newWidth
        else:
            if newHeight < 0 and newWidth < 0:
                pass
            elif newHeight < 0:
                self.width = newWidth
            else:
                self.height = newHeight
            raise ValueError()

    def __str__(self):
        """  converts a Rectangle object into a string  """
        return 'height = %i, width = %i' % (self.height, self.width)


""" code for testing Rectangle objects """
r1 = Rectangle()
print (r1)   # automatically calls __str__(self) method to print  returned value
# both negative
r2 = Rectangle()
try:
    r2.setData(-5,-6)
except:
    print ("both values negative")
finally:
    print (r2)
# one positive one negative
r3 = Rectangle()
try:
    r3.setData(5,-6)
except:
    print ("one positive one negative")
finally:
    print (r3)

r4 = Rectangle()
try:
    r4.setData(-5, 6)
except:
    print ("one positive one negative")
finally:
    print (r4)
