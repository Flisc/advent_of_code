class Button:
    def __init__(self, name, dx, dy):
        self.name = name
        self.dx = dx
        self.dy = dy
    def __repr__(self):
        return f"Button(name={self.name!r}, dx={self.dx}, dy={self.dy})"


class Machine:
    buttons = ()
    tokens = 0
    def __init__(self, ax, ay, bx, by, xt, yt):
        b1 = Button("btn A", ax, ay)
        b2 = Button("btn B", bx, by)
        self.buttons = (b1, b2)
        self.xt = xt
        self.yt = yt
    def __repr__(self):
        return (f"Machine(buttons={self.buttons}, "
                f"prize=(xt={self.xt}, yt={self.yt}))")
