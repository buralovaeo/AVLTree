package AVL;

public class AVLTree {
    private AVLNode root;
    private int operationCount;

    public AVLTree() {
        this.root = null;
        this.operationCount = 0;
    }

    private int getHeight(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    private int getBalanceFactor(AVLNode node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode rotateRight(AVLNode y) {
        operationCount++;
        AVLNode x = y.left;
        AVLNode B = x.right;

        x.right = y;
        y.left = B;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        operationCount++;
        AVLNode y = x.right;
        AVLNode B = y.left;

        y.left = x;
        x.right = B;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Вставка с подсчетом операций
    public int insertWithCounter(int key) {
        operationCount = 0;
        root = insertRec(root, key);
        return operationCount;
    }

    private AVLNode insertRec(AVLNode node, int key) {
        operationCount++;

        if (node == null) {
            operationCount++;
            return new AVLNode(key);
        }

        if (key < node.key) {
            node.left = insertRec(node.left, key);
        } else if (key > node.key) {
            node.right = insertRec(node.right, key);
        } else {
            operationCount++;
            return node;
        }

        operationCount++;
        updateHeight(node);

        int balance = getBalanceFactor(node);
        operationCount += 2;

        // LL
        if (balance > 1 && key < node.left.key) {
            operationCount++;
            return rotateRight(node);
        }

        // RR
        if (balance < -1 && key > node.right.key) {
            operationCount++;
            return rotateLeft(node);
        }

        // LR
        if (balance > 1 && key > node.left.key) {
            operationCount++;
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            operationCount++;
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Поиск с подсчетом операций
    public int searchWithCounter(int key) {
        operationCount = 0;
        searchRec(root, key);
        return operationCount;
    }

    private AVLNode searchRec(AVLNode node, int key) {
        operationCount++;

        if (node == null) {
            return null;
        }

        operationCount++;
        if (key == node.key) {
            return node;
        }

        operationCount++;
        if (key < node.key) {
            return searchRec(node.left, key);
        } else {
            return searchRec(node.right, key);
        }
    }

    // Удаление с подсчетом операций
    public int deleteWithCounter(int key) {
        operationCount = 0;
        root = deleteRec(root, key);
        return operationCount;
    }

    private AVLNode getMinNode(AVLNode node) {
        operationCount++;
        AVLNode current = node;
        while (current.left != null) {
            operationCount++;
            current = current.left;
        }
        return current;
    }

    private AVLNode deleteRec(AVLNode node, int key) {
        operationCount++;

        if (node == null) {
            return null;
        }

        operationCount++;
        if (key < node.key) {
            node.left = deleteRec(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRec(node.right, key);
        } else {
            operationCount++;

            if (node.left == null) {
                operationCount++;
                return node.right;
            } else if (node.right == null) {
                operationCount++;
                return node.left;
            }

            operationCount += 2;
            AVLNode successor = getMinNode(node.right);
            node.key = successor.key;
            node.right = deleteRec(node.right, successor.key);
        }

        operationCount++;
        updateHeight(node);

        int balance = getBalanceFactor(node);
        operationCount += 2;

        // LL
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            operationCount++;
            return rotateRight(node);
        }

        // LR
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            operationCount += 2;
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RR
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            operationCount++;
            return rotateLeft(node);
        }

        // RL
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            operationCount += 2;
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public int getTreeHeight() {
        return getHeight(root);
    }
}
