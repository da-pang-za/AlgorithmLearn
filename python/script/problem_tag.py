


import csv
import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
from collections import *
# from typing import List
from selenium.webdriver.remote.webelement import WebElement
import sys
import os
# print (os.listdir())
# open('./tmp/q4Ids.csv')
# problems = open('./tmp/problems.csv', 'w')
# exit(0)
driver = selenium.webdriver.Chrome()


def waitElement(w_by, w_val) -> WebElement:
    loaded = False
    while (not loaded):
        try:
            time.sleep(0.3)
            e: WebElement = driver.find_element(w_by, w_val)
            loaded = True
        except:
            pass
    return e


def waitElements(w_by, w_val,by,val) -> list[WebElement]:
    loaded = False
    while (not loaded):
        try:
            time.sleep(0.3)
            es: WebElement = driver.find_element(w_by, w_val)
            loaded = True
        except:
            pass
    es: WebElement = driver.find_elements(by, val)
    return es


en_tags = ['dynamic-programming', 'math', 'depth-first-search', 'breadth-first-search', 'greedy'
    , 'binary-search', 'two-pointers', 'stack', 'queue', 'memoization', 'bitmask', 'enumeration'
    , 'monotonic-stack', 'divide-and-conquer', 'union-find', 'sliding-window'
    , 'prefix-sum', 'simulation', 'graph', 'backtracking', 'heap-priority-queue', 'design']

tag2id = defaultdict(list)  # 一个tag多问题
id2tag = defaultdict(list)  # 一个问题多tag

for en_tag in  en_tags:
    driver.get('https://leetcode-cn.com/tag/' + en_tag + '/problemset/')

    tag: str = waitElement(By.XPATH, '//*[@id="lc-content"]/div/div[1]/header/div/div[1]/div[1]/div/div[1]/div/h1').text

    while True:
        trs: list[WebElement] = waitElements(By.XPATH, '//tbody//a',By.XPATH,'//tbody/tr')
        for tr in trs:
            tds: list[WebElement] = tr.find_elements(By.TAG_NAME, 'td')
            pb: str = tds[1].find_element(By.TAG_NAME, 'a').text
            id = pb.split('.')[0]
            name = pb[len(id) + 2:]
            id2tag[id].append(tag)
            tag2id[tag].append(id)
            # print(id)
            # print(name)
        try:
            nextpagebtn: WebElement = driver.find_elements(By.XPATH,
                                                           '//button[@tabindex]')[-1]
            if nextpagebtn.is_enabled():
                nextpagebtn.click()
            else:
                break
        except:
            print("one page only...")
            break

driver.quit()
problems = csv.writer(open('./tmp/problems.csv', 'w'))
tags = csv.writer(open('./tmp/tags.csv', 'w'))

for id, tagList in id2tag.items():
    txt = ''
    if tagList:
        for tag in tagList:
            txt += tag
            txt += ','
        txt = txt[:-1]

    problems.writerow([id, txt])
for tag, idList in tag2id.items():
    txt = ''
    if idList:
        for id in idList:
            txt += id
            txt += ','
        txt = txt[:-1]
    tags.writerow([tag, txt])
