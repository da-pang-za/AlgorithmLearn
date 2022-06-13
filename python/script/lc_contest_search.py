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
addr="https://leetcode-cn.com/contest/weekly-contest-293/ranking/"
page=12
while page<=40:
    driver.get(addr+str(page)+'/')
    if page==12:
        time.sleep(2)
    else:time.sleep(0.5)
    s= driver.find_element(By.TAG_NAME,"body").text

    if(s.__contains__('可爱抱抱呀😥')):
        print(page)
        exit(0)
    page+=1

driver.quit()