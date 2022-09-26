# copy solution from uwi
startTag = "//==START=="
endTag = "//==END=="

uwiPath="/Users/dpzdpz/project/AlgorithmLearn/src/main/java/UWI.java"
def copyProblem(uwiFile, tmpFile):
    uwiFile = open(uwiFile,mode='r')
    tmpFile = open(tmpFile,mode='r+')
    uwi = uwiFile.read()
    template = tmpFile.read()
    solution = uwi[uwi.find(startTag):uwi.find(endTag)]
    a = template[:template.find(startTag)]
    b = template[template.find(endTag):]
    tot = a + solution + b
    # 回退指针
    tmpFile.seek(0)
    # tmpFile.truncate()
    tmpFile.write(tot)

tmpPath="/Users/dpzdpz/project/AlgorithmLearn/src/main/java/codeforces/contest/729/D/CF729D.java"
copyProblem(uwiPath,tmpPath)