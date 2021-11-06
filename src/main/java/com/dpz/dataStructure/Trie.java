package com.dpz.dataStructure;

/**
 * word仅有小写英文字母组成
 */
public class Trie {

    Trie[]nodes;
    boolean flag;//是否为有效的单词结尾
    public Trie() {
        nodes=new Trie[26];
        flag=false;
    }

    public void insert(String word) {
        Trie cur=this;
        for (char c : word.toCharArray()) {
            if(cur.nodes[c-'a']==null){
                cur.nodes[c-'a']=new Trie();
            }
            cur=cur.nodes[c-'a'];
        }
        cur.flag=true;
    }

    public boolean search(String word) {
        Trie cur=this;

        for (char c : word.toCharArray()) {
            if(cur.nodes[c-'a']==null)return false;
            cur=cur.nodes[c-'a'];
        }
        return cur.flag;

    }

    //判断前缀是否存在
    public boolean startsWith(String prefix) {
        Trie cur=this;

        for (char c : prefix.toCharArray()) {
            if(cur.nodes[c-'a']==null)return false;
            cur=cur.nodes[c-'a'];
        }
        return true;
    }
}

