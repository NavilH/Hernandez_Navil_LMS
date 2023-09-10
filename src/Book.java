class Book {
    private int id;
    private final String title;
    private final String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    /** This is the only property with a setter since it is the only
     * one that can change
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  id + "," + title + "," + author;
    }
}