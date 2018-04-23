package report;

import csl.infolab.archiver.ArchiveSetInfolab;
import csl.infolab.archiver.Archiver;
import csl.infolab.archiver.ArchiverGui;

public class RunArchiver {
    public static void main(String[] args) {
        Archiver a = new Archiver();
        ArchiveSetInfolab il = new ArchiveSetInfolab();
        il.apply(a);
        new ArchiverGui(a);
    }
}
