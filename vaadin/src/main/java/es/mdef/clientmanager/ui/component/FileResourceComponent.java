package es.mdef.clientmanager.ui.component;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import es.mdef.clientmanager.s3.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 4/11/14
 * Time: 23:38
 */
@Configurable(preConstruction = true)
public class FileResourceComponent extends HorizontalLayout {



    @Autowired
    private FilesService filesService;

    private Label dateLabel;
    private Label sizeLabel;

    private Button downloadButton;
    private Button deleteButton;

    Button.ClickListener onDeleteListener;


    private static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy hh:mm");
    private static DecimalFormat decimalFormat=new DecimalFormat("#.##'k'");

    public FileResourceComponent(String name, Date date, Long size, final String key){
        this.setSpacing(true);
        downloadButton =new Button(name);
        downloadButton.setStyleName("link");
        this.dateLabel=new Label(sdf.format(date));
        this.sizeLabel=new Label(decimalFormat.format(size / 1024));
        FileDownloader fileDownloader = new FileDownloader(new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                return filesService.downloadFile(key);
            }
        },name));
        fileDownloader.extend(downloadButton);
        deleteButton=new Button("Borrar");
        deleteButton.setStyleName("icon-only borderless");
        deleteButton.setIcon(new ThemeResource("icons/cancelar.svg"));
        deleteButton.setDescription("Borrar");
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                confirmDialog(key);
            }
        });
        this.addComponent(downloadButton);
        this.addComponent(sizeLabel);
        this.addComponent(dateLabel);
        this.addComponent(deleteButton);
    }


    private void confirmDialog(final String key){
        final HorizontalLayout buttonsLayout=new HorizontalLayout();
        buttonsLayout.setWidth(200, Unit.PIXELS);
        buttonsLayout.setHeight(100, Unit.PIXELS);
        buttonsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonsLayout.setSpacing(true);
        final Window confirmDialog=new Window("Desea borrar el fichero",buttonsLayout);
        confirmDialog.center();
        confirmDialog.setModal(true);
        final Button confirm=new Button("Si");

        confirm.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                filesService.deleteFile(key);
                confirmDialog.close();
                removeComponent();
            }
        });

        final Button cancel=new Button("No");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                confirmDialog.close();
            }
        });
        buttonsLayout.addComponent(confirm);
        buttonsLayout.addComponent(cancel);
        getUI().addWindow(confirmDialog);
    }

    public FilesService getFilesService() {
        return filesService;
    }

    public void setFilesService(FilesService filesService) {
        this.filesService = filesService;
    }

    private void removeComponent(){
        HasComponents hasComponents=getParent();
        if(hasComponents instanceof Layout){
            Layout layout=(Layout)hasComponents;
            layout.removeComponent(this);
        }
        System.out.println(hasComponents);

    }
}
