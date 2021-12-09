//
// Created by 大胖子 on 2021/12/9.
//

#ifndef LEETCODECPP_TRIE_H
#define LEETCODECPP_TRIE_H

#include "../import.h"

class Trie {
private:
    bool isEnd;
    vector<Trie *> nodes;

    Trie *searchPrefix(string prefix) {
        Trie *cur = this;
        for (char c:prefix) {
            c -= 'a';
            if (cur->nodes[c] == nullptr) {
                return nullptr;
            }
            cur = cur->nodes[c];
        }
        return cur;
    }

public:
    Trie() {
        nodes.resize(26, nullptr);
        isEnd = false;
    }

//    Trie() : isEnd(false), nodes(26) {}

    void insert(string word) {
        Trie *cur = this;
        for (char c:word) {
            c -= 'a';
            if (cur->nodes[c] == nullptr) {
                cur->nodes[c] = new Trie();
            }
            cur = cur->nodes[c];
        }
        cur->isEnd = true;
    }

    bool search(string word) {
        Trie *node = this->searchPrefix(word);
        return node != nullptr && node->isEnd;
    }

    bool startsWith(string prefix) {
        return this->searchPrefix(prefix) != nullptr;
    }
};


#endif //LEETCODECPP_TRIE_H
