class MessageNode<T> {
    T message;
    String senderUserName;
    MessageNode<T> next;

    MessageNode(T message, String senderUserName) {
        this.message = message;
        this.senderUserName = senderUserName;
        this.next = null;
    }
}

class MessageList<T> {
    private MessageNode<T> head;

    public void addMessage(T message, String senderUserName) {
        MessageNode<T> currentNode = new MessageNode<>(message, senderUserName);
        if (head == null) {
            head = currentNode;
        } else {
            MessageNode<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = currentNode;
        }
    }

    public void displayMessages() {
        MessageNode<T> temp = head;
        while (temp != null) {
            System.out.println("Sender: " + temp.senderUserName + ", Message: " + temp.message);
            temp = temp.next;
        }
        System.out.println();
    }
}
