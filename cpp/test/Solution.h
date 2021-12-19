//
// Created by 大胖子 on 2021/12/9.
//

#ifndef LEETCODECPP_SOLUTION_H
#define LEETCODECPP_SOLUTION_H


#include "../import.h"


class Solution {


    int *parents;
    int rows, cols;

// 初始化，每个节点的父节点都是本身
    void init(int totalNodes) {
        parents = (int *) malloc(sizeof(int) * totalNodes);
        for (int i = 0; i < totalNodes; i++)
            parents[i] = i;
    }

// 查找 node 的祖先节点
    int find(int node) {
        while (parents[node] != node) {
            // 当前节点的父节点 指向父节点的父节点.
            // 保证一个连通区域最终的parents只有一个.
            parents[node] = parents[parents[node]];
            node = parents[node];
        }
        return node;
    }

// 合并
    void Union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);
        if (root1 != root2)
            parents[root1] = root2;
    }

// 判断是否连通
    bool isConnected(int node1, int node2) {
        return find(node1) == find(node2);
    }

// 二维数组转一维数组
    int node(int i, int j) {
        return i * cols + j;
    }

    void solve(char **board, int boardSize, int *boardColSize) {
        if (board == NULL || strlen(board) == 0)
            return;
        rows = boardSize;
        cols = boardColSize[0];
        // 用一个虚拟节点, 边界上的O 的⽗节点都是这个虚拟节点
        init(rows * cols + 1);
        int dummyNode = rows * cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    // 边界上和dummyNode连通
                    if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1)
                        Union(node(i, j), dummyNode);
                    else {
                        // 和四周的 O 连通
                        if (board[i - 1][j] == 'O')
                            Union(node(i, j), node(i - 1, j));
                        if (board[i + 1][j] == 'O')
                            Union(node(i, j), node(i + 1, j));
                        if (board[i][j - 1] == 'O')
                            Union(node(i, j), node(i, j - 1));
                        if (board[i][j + 1] == 'O')
                            Union(node(i, j), node(i, j + 1));
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 如果是和虚拟节点连通的，就是 O ，其他都是 0
                if (isConnected(node(i, j), dummyNode))
                    board[i][j] = 'O';
                else
                    board[i][j] = 'X';
            }
        }
    }
};

#endif //LEETCODECPP_SOLUTION_H
