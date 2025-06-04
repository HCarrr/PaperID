// PaperNetwork.java
package com.example.inventory.model;

public class PaperNetwork {
    private String idPaper;
    private String jenisPaper;
    private boolean statusValidasi;

    public PaperNetwork(String idPaper, String jenisPaper, boolean statusValidasi) {
        this.idPaper = idPaper;
        this.jenisPaper = jenisPaper;
        this.statusValidasi = statusValidasi;
    }

    public String getIdPaper() {
        return idPaper;
    }

    public String getJenisPaper() {
        return jenisPaper;
    }

    public boolean isStatusValidasi() {
        return statusValidasi;
    }

    public void validasiPaper() {
        if (!this.statusValidasi) {
            this.statusValidasi = true;
            System.out.println("[PaperNetwork] Paper '" + idPaper + "' (" + jenisPaper + ") berhasil divalidasi.");
        } else {
            System.out.println("[PaperNetwork] Paper '" + idPaper + "' sudah divalidasi sebelumnya.");
        }
    }
}