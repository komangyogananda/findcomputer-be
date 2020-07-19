package com.kulguy.findcomputer.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ImageRequest {
  @NotBlank
  @NotNull
  private String base64;
  
  @NotBlank
  @NotNull
  private String filename;


  public String getBase64() {
    return this.base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }

  public String getFilename() {
    return this.filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
  
}