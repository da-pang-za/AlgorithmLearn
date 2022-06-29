package com.dpz.dataStructure;

/**
 * 字典树 or 前缀树
 * word仅有小写英文字母组成
 */
public
class Trie {

    Trie[] nodes;
    int cnt;//当前结尾代表的单词的个数

    public Trie() {
        nodes = new Trie[26];
        cnt = 0;

    }

    public void insert(String word) {
        Trie cur = this;
        for (char c : word.toCharArray()) {
            if (cur.nodes[c - 'a'] == null) {
                cur.nodes[c - 'a'] = new Trie();
            }
            cur = cur.nodes[c - 'a'];
        }
        cur.cnt++;
    }

    public int search(String word) {
        Trie cur = this;

        for (char c : word.toCharArray()) {
            if (cur.nodes[c - 'a'] == null) return 0;
            cur = cur.nodes[c - 'a'];
        }
        return cur.cnt;

    }

    //判断前缀是否存在
    public boolean startsWith(String prefix) {
        Trie cur = this;

        for (char c : prefix.toCharArray()) {
            if (cur.nodes[c - 'a'] == null) return false;
            cur = cur.nodes[c - 'a'];
        }
        return true;
    }
}

//01字典树
class Trie01 {

    Trie01[] nodes;
    int cnt;//当前结尾代表的单词的个数

    public Trie01() {
        nodes = new Trie01[2];
        cnt = 0;
    }

    public void insert(int v) {
        Trie01 cur = this;
        for (int i = 31; i >= 0; i--) {
            int bit = (v >> i) & 1;
            if (cur.nodes[bit] == null) {
                cur.nodes[bit] = new Trie01();
            }
            cur = cur.nodes[bit];
        }
        cur.cnt++;
    }

    public int search(int v) {
        Trie01 cur = this;
        for (int i = 31; i >= 0; i--) {
            int bit = (v >> i) & 1;
            if (cur.nodes[bit] == null) return 0;
            cur = cur.nodes[bit];
        }
        return cur.cnt;
    }

}
