from dataStructure.common import TreeNode


# binaryTree  recursion
class Solution:
    p = 0

    def findTilt(self, root: TreeNode) -> int:
        # self.p=0
        self.sum(root)
        return self.p

    def sum(self, root: TreeNode) -> int:
        if root is None: return 0
        # python调用成员函数要用self.
        l = self.sum(root.left)
        r = self.sum(root.right)
        self.p += abs(l - r)
        return root.val + l + r
