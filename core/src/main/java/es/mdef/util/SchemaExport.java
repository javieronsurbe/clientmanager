package es.mdef.util;


import org.hibernate.engine.jdbc.internal.Formatter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * User: jonsurbe
 * Date: 21/03/14
 * Time: 16:31
 */
public class SchemaExport {
    public static void main(String[] args) throws FileNotFoundException {
/*        boolean drop = false;
        boolean create = true;
        String outFile = "schema.sql";
        String delimiter = "";
        String unitName = null;


        Formatter formatter = FormatStyle.DDL.getFormatter();

        Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(unitName, null);
        Configuration hibernateConfiguration = jpaConfiguration.getHibernateConfiguration();

        String[] createSQL = hibernateConfiguration.generateSchemaCreationScript(
                Dialect.getDialect(hibernateConfiguration.getProperties()));
        String[] dropSQL = hibernateConfiguration.generateDropSchemaScript(
                Dialect.getDialect(hibernateConfiguration.getProperties()));

        if (create)
            export(outFile, delimiter, formatter, createSQL);
        if (drop)
            export(outFile, delimiter, formatter, dropSQL);*/
    }

    private static void export(String outFile, String delimiter, Formatter formatter, String[] createSQL) throws FileNotFoundException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outFile);
            for (String string : createSQL) {
                writer.print(formatter.format(string) + "\n" + delimiter + "\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
