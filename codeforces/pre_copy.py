# copy solution from uwi
import sys

startTag = "//==START=="
endTag = "//==END=="

uwiPath="/Users/dpz/Desktop/CS_Knowledge/Alg/src/main/java/UWI.java"
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
    tmpFile.truncate(0)
    tmpFile.write(tot)
    tmpFile.flush()

tmpPath=sys.argv[1]
copyProblem(uwiPath,tmpPath)