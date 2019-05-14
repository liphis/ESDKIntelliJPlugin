package data;

/**
 * The type Fop json.
 */
public class FopJson {

    /**
     * The Database name.
     */
    public final String databaseName;
    /**
     * The Group name.
     */
    public final String groupName;
    /**
     * The Editor mode.
     */
    public final String editorMode;
    /**
     * The Event.
     */
    public final String event;
    /**
     * The Key.
     */
    public final String key;
    /**
     * The Field.
     */
    public final String field;
    /**
     * The Head or table.
     */
    public final String headOrTable;
    /**
     * The Is continue.
     */
    public final String isContinue;
    /**
     * The Handler.
     */
    public final String handler;

    /**
     * Instantiates a new Fop json.
     *
     * @param databaseName the database name
     * @param groupName    the group name
     * @param editorMode   the editor mode
     * @param event        the event
     * @param key          the key
     * @param field        the field
     * @param headOrTable  the head or table
     * @param isContinue   the is continue
     * @param handler      the handler
     */
    public FopJson(final String databaseName, final String groupName, final String editorMode, final String event,
                   final String key, final String field, final String headOrTable, final String isContinue, final String handler) {
        this.databaseName = databaseName;
        this.groupName = groupName;
        this.editorMode = editorMode;
        this.event = event;
        this.key = key;
        this.field = field;
        this.headOrTable = headOrTable;
        this.isContinue = isContinue;
        this.handler = handler;
    }
}
