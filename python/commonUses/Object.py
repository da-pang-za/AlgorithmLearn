from dataStructure.common import ListNode


class Solution:
    dummyNode = ListNode()

    def reverseList(self, head: ListNode) -> ListNode:
        if head is None: return None
        self.dummyNode.next = head
        while head.next is not None:
            next = head.next
            head.next = next.next
            next.next = self.dummyNode.next
            self.dummyNode.next = next
        return self.dummyNode.next
