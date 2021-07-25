package com.company;
public class WordDictionary {
    private boolean is_string=false;
    private WordDictionary next[]=new WordDictionary[26];

    public WordDictionary(){}

    public void addWord(String word){//插入单词
        WordDictionary root=this;
        char w[]=word.toCharArray();
        for(int i=0;i<w.length;++i){
            if(root.next[w[i]-'a']==null)root.next[w[i]-'a']=new WordDictionary();
            root=root.next[w[i]-'a'];
        }
        root.is_string=true;
    }

    public boolean search(String word){//查找单词
        WordDictionary root=this;
        char w[]=word.toCharArray();
        return help(w,0,root);
    }
    boolean help(char[]w,int index,WordDictionary root){
        for(int i=index;i<w.length;++i){
            if(w[i]!='.'){
                if(root.next[w[i]-'a']==null)return false;
                root=root.next[w[i]-'a'];
            }
            else {
                for (int j = 0; j <26 ; j++) {
                    if(root.next[j]!=null)
                        if(help(w,i+1,root.next[j]))return true;
                }
                return false;
            }

        }
        return root.is_string;
    }

}