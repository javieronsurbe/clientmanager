package es.mdef.clientmanager.ui.component;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.*;
import com.vaadin.ui.Upload.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: jonsurbe
 * Date: 27/10/14
 * Time: 14:05
 */
public class UploadComponent extends VerticalLayout implements StartedListener, FinishedListener,
        FailedListener, ProgressListener, DropHandler {

    private static final long serialVersionUID = 1L;


    // Ui components
    protected ProgressBar progressBar;
    protected Upload upload;
    protected Upload.Receiver receiver;

    // Additional listeners can be attached to the upload components
    protected List<Upload.FinishedListener> finishedListeners = new ArrayList<FinishedListener>();
    protected List<StartedListener> startedListeners = new ArrayList<StartedListener>();
    protected boolean showGenericFailureMessage = true;
    protected List<FailedListener> failedListeners = new ArrayList<FailedListener>();
    protected List<ProgressListener> progressListeners = new ArrayList<ProgressListener>();


    public UploadComponent(String description, Receiver receiver) {
        this.receiver = receiver;

        init(description);
    }

    // UI initialisation ----------------------------------------------------------------------------

    protected void init(String description) {
        setSpacing(true);
        setSizeFull();

        addDescription(description);
        addUpload();
        addOrLabel();
        addDropPanel();
    }

    protected void addDescription(String description) {
        if(description != null) {
            Label descriptionLabel = new Label(description);
            addComponent(descriptionLabel);
        }
    }

    protected void addUpload() {
        this.upload = new Upload(null, receiver);
        upload.setButtonCaption("Select");
        upload.setImmediate(true);
        addComponent(upload);
        setComponentAlignment(upload, Alignment.MIDDLE_CENTER);

        // register ourselves as listener for upload events
        upload.addListener((StartedListener) this);
        upload.addListener((FailedListener) this);
        upload.addListener((FinishedListener) this);
        upload.addListener((ProgressListener) this);
    }

    protected void addOrLabel() {
        Label orLabel = new Label("or");
        orLabel.setSizeUndefined();
        addComponent(orLabel);
        setComponentAlignment(orLabel, Alignment.MIDDLE_CENTER);
    }

    protected void addDropPanel() {
        Panel dropPanel = new Panel();
        DragAndDropWrapper dragAndDropWrapper = new DragAndDropWrapper(dropPanel);
        dragAndDropWrapper.setDropHandler(this);
        dragAndDropWrapper.setWidth("80%");
        addComponent(dragAndDropWrapper);
        setComponentAlignment(dragAndDropWrapper, Alignment.MIDDLE_CENTER);

    }

    // File upload event handling -------------------------------------------------------------------

    public void uploadStarted(StartedEvent event) {
        removeAllComponents(); // Visible components are replaced by a progress bar

        this.progressBar = new ProgressBar();
        addComponent(progressBar);
        setComponentAlignment(progressBar, Alignment.MIDDLE_CENTER);

        for (StartedListener startedListener : startedListeners) {
            startedListener.uploadStarted(event);
        }
    }

    public void updateProgress(long readBytes, long contentLength) {
        progressBar.setValue(new Float(readBytes / (float) contentLength));

        for (ProgressListener progressListener : progressListeners) {
            progressListener.updateProgress(readBytes, contentLength);
        }
    }

    public void uploadFinished(FinishedEvent event) {
        // Hide progress indicator
        progressBar.setVisible(false);
        for (FinishedListener finishedListener : finishedListeners) {
            finishedListener.uploadFinished(event);
        }
    }

    public void uploadFailed(FailedEvent event) {
        for (FailedListener failedListener : failedListeners) {
            failedListener.uploadFailed(event);
        }
    }

    // Drag and drop handling (DropHandler) ---------------------------------------------------------

    public void drop(DragAndDropEvent event) {
        DragAndDropWrapper.WrapperTransferable transferable = (DragAndDropWrapper.WrapperTransferable) event.getTransferable();
        Html5File[] files = transferable.getFiles();
        if (files.length > 0) {
            final Html5File file = files[0]; // only support for one file upload at this moment
            file.setStreamVariable(new StreamVariable() {

                private static final long serialVersionUID = 1L;

                public void streamingStarted(StreamingStartEvent event) {
                    uploadStarted(null); // event doesnt matter here
                }
                public void streamingFinished(StreamingEndEvent event) {
                    uploadFinished(null); // event doesnt matter here
                }
                public void streamingFailed(StreamingErrorEvent event) {
                    uploadFailed(null);
                }
                public void onProgress(StreamingProgressEvent event) {
                    updateProgress(event.getBytesReceived(), event.getContentLength());
                }
                public boolean listenProgress() {
                    return true;
                }
                public boolean isInterrupted() {
                    return false;
                }
                public OutputStream getOutputStream() {
                    return receiver.receiveUpload(file.getFileName(), file.getType());
                }
            });
        }
    }

    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }

    // Upload Listeners ----------------------------------------------------------------------------

    public void addFinishedListener(FinishedListener finishedListener) {
        finishedListeners.add(finishedListener);
    }

    public void addStartedListener(StartedListener startedListener) {
        startedListeners.add(startedListener);
    }

    public void addFailedListener(FailedListener failedListener) {
        failedListeners.add(failedListener);
    }

    public void addProgressListener(ProgressListener progressListener) {
        progressListeners.add(progressListener);
    }



}
