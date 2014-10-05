package com.capcarde.Beans;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="fileDownload")
public class FileDownloadController {

    public FileDownloadController() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/com.zip");
        file = new DefaultStreamedContent(stream, "application/octet-stream", "com.zip");
    }

    public StreamedContent getFile() {
        return file;
    }
    private StreamedContent file;
}
