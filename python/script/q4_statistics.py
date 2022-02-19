


import csv
import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
from collections import *
# from typing import List
from selenium.webdriver.remote.webelement import WebElement

tag2id = defaultdict(list)  # 一个tag多问题
id2tag = defaultdict(list)  # 一个问题多tag

q4tag2id=defaultdict(list)
q4id2rating={}
scoreMap = {}
# init
scores = [1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600,2700, 5000]
for score in scores:
    scoreMap[score] = defaultdict(list)  # 各个tag的数量

problems = csv.reader(open('./tmp/problems.csv', 'r'))
tags = csv.reader(open('./tmp/tags.csv', 'r'))

for line in problems:
    id = line[0]
    ts = line[1].split(',')
    for t in ts:
        id2tag[id].append(t)
for line in tags:
    tag = line[0]
    ids = line[1].split(',')
    for id in ids:
        tag2id[tag].append(id)

q4s = csv.reader(open('./tmp/q4Ids.csv', 'r'))

i = 0
for line in q4s:
    id = line[0]
    rating = int(line[1])
    if rating == 0:
        continue
    tags = id2tag[id]
    for score in scores:
        if rating <= score:
            for tag in tags:
                scoreMap[score][tag].append(id)
                q4id2rating[id]=rating
                q4tag2id[tag].append(id)
            break


for score, tc in scoreMap.items():
    print("======" + score.__str__() + "分段=======")
    for tag, ids in tc.items():
        print(tag + ":" + ids.__len__().__str__() + "题:")
        print(ids)
print("========各tag平均分==========")
for tag,ids in q4tag2id.items():
    print(tag+":"+(sum([q4id2rating[id] for id in ids])/len(ids)).__str__())


