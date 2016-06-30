package org.salwasser.structures;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zac on 6/29/2016.
 */
public class BinarySearchTree<T extends Comparable> {
    private class TreeNode {
        public TreeNode lessThan;
        public TreeNode greaterThan;
        public T val;
        public int height;

        public TreeNode(T newVal) {
            this.val = newVal;
            this.height = 0;
            this.lessThan = null;
            this.greaterThan = null;
        }

    }

    private TreeNode root = null;

    private void rebalanceTowardGreaterThan(TreeNode node, TreeNode parentNode, boolean childGTParent) {
        if (node.lessThan == null) {
            return;
        }

        if (!childGTParent) {
            parentNode.lessThan = node.lessThan;
        } else {
            parentNode.greaterThan = node.lessThan;
        }

        if (node.lessThan.greaterThan != null) {
            node.lessThan = node.lessThan.greaterThan;
        }

        if (!childGTParent) {
            parentNode.lessThan.greaterThan = node;
        } else {
            parentNode.greaterThan.greaterThan = node;
        }

        int ltHeight = 0;
        int gtHeight = 0;
        if (node.lessThan != null) {
            ltHeight = node.lessThan.height;
        }
        if (node.greaterThan != null) {
            gtHeight = node.greaterThan.height;
        }
        node.height = Math.max(ltHeight, gtHeight) + 1;

    }

    private void rebalanceTowardLessThan(TreeNode node, TreeNode parentNode, boolean childGTParent) {
        if (node.greaterThan == null) {
            return;
        }

        if (!childGTParent) {
            parentNode.lessThan = node.greaterThan;
        } else {
            parentNode.greaterThan = node.greaterThan;
        }

        if (node.greaterThan.lessThan != null) {
            node.greaterThan = node.greaterThan.lessThan;
        }

        if (!childGTParent) {
            parentNode.lessThan.lessThan = node;
        } else {
            parentNode.greaterThan.lessThan = node;
        }

        int ltHeight = 0;
        int gtHeight = 0;
        if (node.lessThan != null) {
            ltHeight = node.lessThan.height;
        }
        if (node.greaterThan != null) {
            gtHeight = node.greaterThan.height;
        }
        node.height = Math.max(ltHeight, gtHeight) + 1;
    }

    private void checkBalance(TreeNode node, TreeNode parentNode, boolean childGTParent) {
        if (node.lessThan != null) {
            checkBalance(node.lessThan, node, false);
        }
        if (node.greaterThan != null) {
            checkBalance(node.greaterThan, node, true);
        }
        if (node.greaterThan.height - node.lessThan.height > 1) {
            rebalanceTowardLessThan(node, parentNode, childGTParent);
        } else if (node.lessThan.height - node.greaterThan.height > 1) {
            rebalanceTowardGreaterThan(node, parentNode, childGTParent);
        }
    }

    private void checkBalance() {
        TreeNode tmpRoot = new TreeNode(root.val);
        tmpRoot.greaterThan = root;
        checkBalance(root, tmpRoot, true);
        root = tmpRoot.greaterThan;
    }

    private void hang(T toHang, TreeNode node) {
        int ltHeight = 0;
        int gtHeight = 0;

        if (toHang.equals(node.val)) {
            return;
        } else if (toHang.compareTo(node.val) < 0) {
            if (node.greaterThan != null) {
                gtHeight = node.greaterThan.height;
            }
            if (node.lessThan == null) {
                node.lessThan = new TreeNode(toHang);
            } else {
                hang(toHang, node.lessThan);
                ltHeight = node.lessThan.height;
            }
        } else {
            if (node.lessThan != null) {
                ltHeight = node.lessThan.height;
            }
            if (node.greaterThan == null) {
                node.greaterThan = new TreeNode(toHang);
            } else {
                hang(toHang, node.greaterThan);
                gtHeight = node.greaterThan.height;
            }
        }

        node.height = Math.max(ltHeight, gtHeight) + 1;
    }

    public void insert(T toInsert) {
        if (root == null) {
            root = new TreeNode(toInsert);
        }

        hang(toInsert, root);
        checkBalance();
    }

    private T popSmallest(TreeNode node, TreeNode parentNode, boolean childGT) {
        T retval;
        int gtHeight = 0;
        int ltHeight = 0;

        if (node.lessThan != null) {
            retval = popSmallest(node.lessThan, node, false);
        } else {
            retval = node.val;
            if (childGT) {
                parentNode.greaterThan = node.greaterThan;
            } else {
                parentNode.lessThan = node.greaterThan;
            }
        }

        if (node.greaterThan != null) {
            gtHeight = node.greaterThan.height;
        }
        if (node.lessThan != null) {
            ltHeight = node.lessThan.height;
        }
        node.height = Math.max(ltHeight, gtHeight) + 1;
        return retval;

    }

    private void unHang(T toUnhang, TreeNode node, TreeNode nodeParent) {
        int ltHeight = 0;
        int gtHeight = 0;

        if (toUnhang.equals(node.val)) {
            if (node.lessThan == null) {
                if (node.greaterThan == null) {
                    if (node.val.compareTo(nodeParent.val) < 0) {
                        nodeParent.lessThan = null;
                    } else {
                        nodeParent.greaterThan = null;
                    }
                    return;
                }
                if (node.val.compareTo(nodeParent.val) < 0) {
                    nodeParent.lessThan = node.greaterThan;
                } else {
                    nodeParent.greaterThan = node.greaterThan;
                }
                return;
            }
            if (node.greaterThan == null) {
                if (node.val.compareTo(nodeParent.val) < 0) {
                    nodeParent.lessThan = node.lessThan;
                } else {
                    nodeParent.greaterThan = node.lessThan;
                }
                return;
            }
            node.val = popSmallest(node.greaterThan, node, true);
        } else {
            if (toUnhang.compareTo(node.val) < 0) {
                if (node.lessThan == null) {
                    return;
                }
                unHang(toUnhang, node.lessThan, node);
            } else {
                if (node.greaterThan == null) {
                    return;
                }
                unHang(toUnhang, node.greaterThan, node);
            }
        }

        if (node.greaterThan != null) {
            gtHeight = node.greaterThan.height;
        }
        if (node.lessThan != null) {
            ltHeight = node.lessThan.height;
        }
        node.height = Math.max(ltHeight, gtHeight) + 1;
    }

    public void remove(T toRemove) {
        if (root == null) {
            return;
        }

        if (root.val.equals(toRemove)) {
            if (root.lessThan == null) {
                if (root.greaterThan == null) {
                    root = null;
                    return;
                }
                root = root.greaterThan;
                return;
            }
            if (root.greaterThan == null) {
                root = root.lessThan;
                return;
            }
            root.val = popSmallest(root.greaterThan, root, true);
        } else if (toRemove.compareTo(root.val) < 0) {
            if (root.lessThan != null) {
                unHang(toRemove, root.lessThan, root);
            } else {
                return;
            }
        } else {
            if (root.greaterThan != null) {
                unHang(toRemove, root.greaterThan, root);
            } else {
                return;
            }
        }

        int ltHeight = 0;
        int gtHeight = 0;
        if (root.lessThan != null) {
            ltHeight = root.lessThan.height;
        }
        if (root.greaterThan != null) {
            gtHeight = root.greaterThan.height;
        }
        root.height = Math.max(ltHeight, gtHeight) + 1;

        checkBalance();
    }

    public boolean contains(T toLookup) {
        return (lookup(toLookup) != null);
    }

    private T rootedLookup(T toLookup, TreeNode node) {
        if (toLookup.equals(node.val)) {
            return node.val;
        }

        if (toLookup.compareTo(node.val) < 0) {
            if (node.lessThan == null) {
                return null;
            }
            return rootedLookup(toLookup, node.lessThan);
        }

        if (node.greaterThan == null) {
            return null;
        }

        return rootedLookup(toLookup, node.greaterThan);
    }

    public T lookup(T toLookup) {
        return rootedLookup(toLookup, root);
    }

    private void traverseInOrder(ArrayList<T> list, TreeNode node) {
        if (node.lessThan != null) {
            traverseInOrder(list, node.lessThan);
        }

        list.add(node.val);

        if (node.greaterThan != null) {
            traverseInOrder(list, node.greaterThan);
        }
    }

    public List<T> asSortedList() {
        ArrayList<T> retval = new ArrayList<T>();
        traverseInOrder(retval, root);
        return retval;
    }

    private void bfTraverse(List<T> toAddto, List<Integer> offsets, TreeNode node) {
        int idx = offsets.size();
        int totalOffset = 2 ^ (offsets.size() - 1);

        for (Integer offset : offsets) {
            totalOffset += idx * offset;
            idx--;
        }

        toAddto.set(totalOffset, node.val);

        if (node.lessThan != null) {
            List<Integer> newOffsets = new ArrayList<Integer>(offsets);
            newOffsets.add(0);
            bfTraverse(toAddto, newOffsets, node.lessThan);
        }
        if (node.greaterThan != null) {
            List<Integer> newOffsets = new ArrayList<Integer>(offsets);
            newOffsets.add(1);
            bfTraverse(toAddto, newOffsets, node.greaterThan);
        }
    }

    @Override
    public String toString() {
        long arraySize = 2 ^ root.height;
        if (arraySize > Integer.MAX_VALUE) {
            return "Tree is way too big to perform breadth traversal.";
        }
        ArrayList<T> toPrint = new ArrayList<T>();
        toPrint.ensureCapacity((int)arraySize);
        toPrint.set(0, root.val);
        if (root.lessThan != null) {
            List<Integer> offsets = new ArrayList<Integer>();
            offsets.add(0);
            bfTraverse(toPrint, offsets, root.lessThan);
        }
        if (root.greaterThan != null) {
            List<Integer> offsets = new ArrayList<Integer>();
            offsets.add(1);
            bfTraverse(toPrint, offsets, root.greaterThan);
        }
        return toPrint.toString();
    }

}
