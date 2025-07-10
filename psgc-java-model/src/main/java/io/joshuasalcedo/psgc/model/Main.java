package io.joshuasalcedo.psgc.model;

import io.joshuasalcedo.psgc.model.constants.PsgcDownloadUrl;
import io.joshuasalcedo.psgc.model.exception.PSGCException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws PSGCException {
        List<PsgcDownloadUrl.FileURL> list = PsgcDownloadUrl.getPsgcDownloadUrls();

       list.forEach(x-> System.out.printf("publication: %s\nsummary: %s\nsummary of changes %s\n",
                x.getPublicationDownloadUrl().toString(),
                x.getSummaryDownloadUrl().toString(),
                x.getSummaryChangesDownloadUrl().toString()));
    }
}
