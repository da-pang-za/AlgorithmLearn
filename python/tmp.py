class TextJustify:
    def __init__(self):
        self.memo = {}
        pass

        # function to define the badness of [i:j], w.r.t. line_width

    def badness(self, words, i, j, line_width):
        # print("computing badness for",i,j)
        n_words = 0
        # calculate total length of words
        len_words = 0
        for idx in range(i, j):
            # print("inside loop",words[idx])
            len_words += len(words[idx])
            n_words += 1
        # print("len of word",len_words)
        n_spaces = n_words - 1
        # then total word length
        total_line_length = (len_words + n_spaces)
        # print("line_width",total_line_length,line_width)
        # fix the penalty condition
        # print(line_width-total_line_length)
        if total_line_length <= line_width:
            return pow((line_width - total_line_length), 3)
        else:
            return float('inf')

    # s is a string containing all collection of words
    # line_width is the width of each line where the words have to be justified
    def dp_justify(self, words, line_width):
        # print("words",words,line_width)
        # invoke the dp at this stage
        self.dp(words, 0, line_width)
        print(self.memo)
        return
        # assume that we are interested in computing dp[i],i.e. the word starting at i.

    # then start from (i+1,n+1) and choose the min value

    def dp(self, words, i, line_width):
        n = len(words)
        if i == n:
            self.memo[n] = (0, None)
            return 0
        # if already memoized return
        if i in self.memo.keys():
            return self.memo[i][0]
        cost = float('inf')
        parent_pointer = None
        for j in range(i + 1, n + 1):
            # compute the penalty
            penalty = self.badness(words, i, j, line_width) + self.dp(words, j, line_width)
            # print("penalty is",penalty)
            if penalty < cost:
                cost = penalty
                parent_pointer = j

        # memoize the solution
        self.memo[i] = (cost, parent_pointer)
        return cost


def main():
    # justifier
    justifier = TextJustify()
    # create strings
    strings = [("oneword", 10), ("one line", 10), ("two lines", 6), \
               ("blah blah blah blah reallylongword", 14), ("blah blah blah blah reallylongword 1", 14)]

    txt1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + \
           "Praesent varius urna eget lacinia pharetra. " + \
           "Vivamus lacinia in dui vitae sodales. " + \
           "Aliquam auctor justo nec condimentum pretium. " + \
           "Curabitur egestas tellus dolor, vel tempus lacus blandit vitae. " + \
           "Cras dapibus scelerisque ex nec gravida."

    strings.append((txt1, 60))
    strings.append((txt1, 70))
    strings.append((txt1, 80))
    strings.append((txt1, 90))
    strings.append((txt1, 100))
    # for string in strings:
    #     justifier.dp_justify(string[0].split(' '),string[1])
    justifier.dp_justify(strings[-1][0].split(' '), strings[-1][1])


main()