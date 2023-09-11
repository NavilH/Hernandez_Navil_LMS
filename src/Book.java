/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 09-10-2023
 */

/**This class defines the properties of the Book objects contained
 * in the collection
 */
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