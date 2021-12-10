//
// Created by 大胖子 on 2021/12/9.
//

#ifndef LEETCODECPP_SOLUTION_H
#define LEETCODECPP_SOLUTION_H


#include "../import.h"


class Solution {
public:
    int wl;
    bool found;
    // 记录了单词是从哪些单词扩展而来，key：单词，value：单词列表，这些单词可以变换到 key ，它们是一对多关系
    unordered_map<string, vector<string>> from;
    vector<int> step;
    unordered_set<string> *dict;
    string beginWord, endWord;

    vector<vector<string> > findLadders(string _beginWord, string _endWord, vector<string> &wordList) {
        step.resize(2);//0正向
        beginWord = _beginWord;
        endWord = _endWord;
        vector<vector<string> > res;
        // 因为需要快速判断扩展出的单词是否在 wordList 里，因此需要将 wordList 存入哈希表，这里命名为「字典」
        unordered_set<string> t = {wordList.begin(), wordList.end()};
        dict=&t;
        // 特殊用例判断
        if (dict->find(endWord) == dict->end()) {
            return res;
        }
        wl = beginWord.length();
        // 第 1 步：广度优先遍历建图
        // 记录扩展出的单词是在第几次扩展的时候得到的，key：单词，value：在广度优先遍历的第几层
        unordered_map<string, int> steps1;
        unordered_map<string, int> steps2;
        steps1[beginWord] = 0;
        steps2[endWord] = 0;
        deque<string> q1;
        deque<string> q2;
        q1.push_back(beginWord);
        q2.push_back(endWord);

        while (!q1.empty() && !q2.empty() && !found) {
            if (q1.size() < q2.size()) {
                update(q1, steps1, steps2, 0);
            } else {
                update(q2, steps2, steps1, 1);
            }
        }

        // 第 2 步：深度优先遍历找到所有解，从 endWord 恢复到 beginWord ，所以每次尝试操作 path 列表的头部
        if (found) {
            deque<string> path;
            path.push_back(endWord);
            dfs(from, path, endWord, res);
        }
        return res;

    }

private:

    void update(deque<string> &queue, unordered_map<string, int> &steps1, unordered_map<string, int> &steps2, int flag) {
        step[flag]++;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            string currWord = queue.front();
            queue.pop_front();
            string nextWord = currWord;
            // 将每一位替换成 26 个小写英文字母
            for (int j = 0; j < wl; j++) {
                char origin = currWord[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    nextWord[j] = c;

                    if (dict->find(nextWord) == dict->end() ||
                        (steps1.find(nextWord) != steps1.end() && step[flag] > steps1[nextWord])) {
                        continue;
                    }
                    if (steps1.find(nextWord) == steps1.end()
                        || (steps1.find(nextWord) != steps1.end() && step[flag] == steps1[nextWord])) {
                        if (flag == 0) {
                            from[nextWord].push_back(currWord);
                        } else {
                            from[currWord].push_back(nextWord);
                        }
                    }

                    // 这一层扩展出的单词进入队列
                    queue.push_back(nextWord);
                    // 记录 nextWord 的 step
                    steps1[nextWord] = step[flag];
                    //当前层找到了
                    if (steps2.find(nextWord) != steps2.end()) {
                        found = true;
                    }
                }
                //改回原单词
                nextWord[j] = origin;
            }
        }
    }


public:

    void dfs(unordered_map<string, vector<string>> &from, deque<string> &path, string cur, vector<vector<string>> &res) {
        if (cur==beginWord) {
            res.push_back({path.begin(), path.end()});
            return;
        }
        for (auto precursor: from[cur]) {
            path.push_front(precursor);
            dfs(from, path, precursor, res);
            path.pop_front();
        }
    }

};


#endif //LEETCODECPP_SOLUTION_H
