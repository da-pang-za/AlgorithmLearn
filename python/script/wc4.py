import csv
import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
from collections import *
from typing import List
from selenium.webdriver.remote.webelement import WebElement

# 统计tag 数量
tagStatistics = defaultdict(int)
# 每道题的分数 id->rating
ratings = []
# id -> name tags
tags = []
names = []
en_names = []
links = []
ids = []

driver = selenium.webdriver.Chrome()
wcCollection = 'https://leetcode-cn.com/circle/article/I9DWmo/'
if __name__ == '__main__':
    # driver = webdriver.Chrome()

    driver.get(wcCollection)

    q4Ids = open('./tmp/q4Ids.csv', 'w')
    loaded = False
    while (not loaded):
        try:
            time.sleep(3)
            q4Collection: WebElement = driver.find_element(By.XPATH,
                                                           '/html/body/div[1]/div/div[2]/div/div/div/div/div[2]/div[3]')
            loaded = True
        except:
            pass

    ps: List[WebElement] = q4Collection.find_elements(By.TAG_NAME, 'p')
    end = len(ps)
    #
    for i in range(1, end):
        qs: List[WebElement] = ps[i].find_elements(By.TAG_NAME, 'a')
        q4 = qs[-1]
        txt = str(q4.text)

        id = txt.split('.')[0]
        if not id.isdigit():
            continue
        name = txt[len(id) + 1:]
        link = str(q4.get_attribute('href'))
        en_name = link.split('/')[-1].replace('-', ' ').replace(' th ', '-th ')

        ids.append(int(id))
        names.append(name)
        links.append(link)
        en_names.append(en_name)

    # ============================================
    driver.get('https://zerotrac.github.io/leetcode_problem_rating/')

    loaded = False
    while (not loaded):
        try:
            time.sleep(3)
            search: WebElement = driver.find_element(By.TAG_NAME, 'input')
            loaded = True
        except:
            pass
    for id, en_name in zip(ids, en_names):
        # search: WebElement = driver.find_element(By.TAG_NAME, 'input')

        if id >= 746:
            search.clear()
            search.send_keys(id)
            time.sleep(0.1)
            idFilter = '//*[@id="wrapper"]/div/div[2]/table/tbody/tr/td[1]'
            td1s: List[WebElement] = driver.find_elements(By.XPATH, idFilter)

            for i,r in enumerate(td1s):
                if int(r.text) == id:
                    trFilter = '//*[@id="wrapper"]/div/div[2]/table/tbody/tr'
                    tr:WebElement=driver.find_elements(By.XPATH,trFilter)[i]

                    r = tr.find_elements(By.TAG_NAME,'td')[-1].text
                    break
            rating = int(r)
        else:
            rating = 0
        ratings.append(rating)
    q4Ids = csv.writer(q4Ids)
    q4Ids.writerows([
        [id, rating, name, en_name]
        for id, name, en_name, rating in zip(ids, names, en_names, ratings)
    ])
    # q4Ids.writelines(
    #     [str(id) +'\t' + str(rating) + '\t' + name + '\t' + en_name +  '\n'
    #      for id, name, en_name, rating in zip(ids, names, en_names, ratings)])

    driver.quit()
