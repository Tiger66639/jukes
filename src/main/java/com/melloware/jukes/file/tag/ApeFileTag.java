package com.melloware.jukes.file.tag;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.exceptions.CannotWriteException;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;

import com.jgoodies.uif.application.Application;
import com.jgoodies.uif.util.ResourceUtils;
import com.melloware.jukes.exception.MusicTagException;
import com.melloware.jukes.gui.view.MainFrame;
import com.melloware.jukes.util.MessageUtil;



/**
 * MusicTag class used for editing  APE file tags. 
 * <p>
 * The Entagged (http://entagged.sourceforge.net/) library is used to read these
 * types of Tags.
 * <p>
 * Copyright (c) 2006 Melloware, Inc. <http://www.melloware.com>
 * @author Emil A. Lefkof III <info@melloware.com>
 * @version 4.0
 * AZ (C) 2010
 */
public final class ApeFileTag extends MusicTag {

   private static final Log LOG = LogFactory.getLog(ApeFileTag.class);
   private AudioFile audioFile;


   /**
    * Constructor that accepts a file.
    * <p>
    * @param aFile the file to open
    * @throws MusicTagException if any error occurs loading tag
    */
   public ApeFileTag(File aFile) throws MusicTagException {
      super(aFile);
        try {
         // create the audio file object
         this.audioFile = AudioFileIO.read(aFile);
         // initialize all tags
         initializeTags();
      } catch (CannotReadException ex) {
         LOG.error(ex.getMessage(), ex);
         throw new MusicTagException("CannotReadException opening Music File Tag." + aFile.getAbsolutePath() + "\n\n"
                  + ex.getMessage());
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         throw new MusicTagException("Unexpected exception opening Music File Tag." + aFile.getAbsolutePath() + "\n\n"
                  + ex.getMessage());
      }

   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getArtist()
    */
   public String getArtist() {
		
	  String Artist = "";
	  if (this.artist == null) {

    	  if ( audioFile.getTag().get(settings.getAlbumArtistTag()).size() != 0){
    		  Artist = audioFile.getTag().get(settings.getAlbumArtistTag()).get(0).toString().trim();
    	  }
    	  if (Artist == "") {
    	   if ( audioFile.getTag().get("Album artist").size() != 0){
    		  Artist = audioFile.getTag().get("Album artist").get(0).toString().trim();
    	   }
    	  }
    	  if (Artist == "") {
       	   if ( audioFile.getTag().getFirstArtist() != null){
    	   this.artist = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstArtist(), NO_TAG).trim();
       	   }
    	  }
    	  else {
    	    this.artist = Artist;  
    	  }
	  }
      return this.artist;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getBitRate()
    */
   public Long getBitRate() {
      if (this.bitRate == null) {
         this.bitRate = Long.valueOf(audioFile.getBitrate());
      }
      return this.bitRate;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getComment()
    */
   public String getComment() {
      if (StringUtils.isBlank(this.comment)) {
         this.comment = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstComment(), "").trim();
      }
      return this.comment;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getCopyrighted()
    */
   public String getCopyrighted() {
      return "No";
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getDisc()
    */
   public String getDisc() {
   /** AZ **/		
   	 if (StringUtils.isBlank(this.disc)) {
   	 String CD_Number = "";	 
     this.disc = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstAlbum(), NO_TAG).trim();
     if (settings.isUseCDNumber()) {	 
       if (audioFile.getTag().get("Disc").size() != 0) {
       CD_Number = " - CD " + audioFile.getTag().get("Disc").get(0).toString().trim(); 
       this.disc = this.disc + CD_Number;	
       }
      }
   	 }
     return this.disc;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getEmphasis()
    */
   public String getEmphasis() {
      return "None";
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getEncodedBy()
    */
   public String getEncodedBy() {
      if (StringUtils.isBlank(this.encodedBy)) {
         this.encodedBy = System.getProperty("application.name");
      }
      return this.encodedBy;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getFrequency()
    */
   public String getFrequency() {
      return Integer.toString(audioFile.getSamplingRate());

   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getGenre()
    */
   public String getGenre() {
      if (StringUtils.isBlank(this.genre)) {
         this.genre = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstGenre(), "Other").trim();
      }
      return this.genre;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getHeader()
    */
   public Map getHeader() {
      return header;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getLayer()
    */
   public String getLayer() {
      return audioFile.getEncodingType();
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getMode()
    */
   public String getMode() {
      return audioFile.getEncodingType();
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getTitle()
    */
   public String getTitle() {
      if (StringUtils.isBlank(this.title)) {

         String temp = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstTitle(), NO_TAG).trim();

         // if this is the max length for a tag, or it begins with "Track"
         // try and grab by filename it may be longer
         if ((temp.length() == 30) || (temp.equals(NO_TAG)) || (temp.startsWith("Track"))) {
            temp = extractTitleFromFilename();
            LOG.debug("Filename extracted");
         }

         // return v2 tag else if empty return the v1 tag
         this.title = StringUtils.defaultIfEmpty(temp, NO_TAG).trim();
      }
      return this.title;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getTrack()
    */
   public String getTrack() {
      if (StringUtils.isBlank(this.track)) {
         int trackNum = 0;
         String tracknumber = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstTrack(), "X").trim();
         if (StringUtils.isNumeric(tracknumber)) {
            trackNum = Integer.parseInt(tracknumber);
         }

         // return v2 tag else if empty return the v1 tag
         this.track = StringUtils.leftPad(String.valueOf(trackNum), 2, "0").trim();

      }
      return this.track;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getTrackLength()
    */
   public long getTrackLength() {
      if (this.trackLength > 1) {
         return this.trackLength;
      }
      this.trackLength = audioFile.getLength();
      return this.trackLength;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getVersion()
    */
   public String getVersion() {
      return audioFile.getEncodingType();
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#getYear()
    */
   public String getYear() {
      if (StringUtils.isBlank(this.year)) {
         this.year = StringUtils.defaultIfEmpty(audioFile.getTag().getFirstYear(), CURRENT_YEAR).trim();
      }
      return this.year;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setArtist(java.lang.String)
    */
   public void setArtist(String aArtist) {
      this.artist = StringUtils.defaultIfEmpty(aArtist, NO_TAG).trim();
      try {
         audioFile.getTag().setArtist(this.artist);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: " + ex.getMessage()); //AZ
      }

   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setComment(java.lang.String)
    */
   public void setComment(String aComment) {
      this.comment = StringUtils.defaultIfEmpty(aComment, "").trim();
      try {
         audioFile.getTag().setComment(this.comment);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: " + ex.getMessage()); //AZ
      }
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setDisc(java.lang.String)
    */
   public void setDisc(String aDisc) {
      this.disc = StringUtils.defaultIfEmpty(aDisc, NO_TAG).trim();
      try {
         audioFile.getTag().setAlbum(this.disc);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: "+ex.getMessage()); //AZ
      }

   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setEncodedBy(java.lang.String)
    */
   public void setEncodedBy(String aEncodedBy) {
	  //AZ - Do not fill EncodedBy with "Jukes" 
      //this.encodedBy = StringUtils.defaultIfEmpty(aEncodedBy, System.getProperty("application.name")).trim();
      this.encodedBy = aEncodedBy;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setGenre(java.lang.String)
    */
   public void setGenre(String aGenre) {
      this.genre = StringUtils.defaultIfEmpty(aGenre, NO_TAG).trim();
      try {
         audioFile.getTag().setGenre(this.genre);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: "+ex.getMessage()); //AZ
      }

   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setTitle(java.lang.String)
    */
   public void setTitle(String aTitle) {
      this.title = StringUtils.defaultIfEmpty(aTitle, NO_TAG).trim();

      try {
         audioFile.getTag().setTitle(this.title);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: "+ ex.getMessage()); //AZ
      }
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setTrack(java.lang.String)
    */
   public void setTrack(String aTrack) {
      setTrack(aTrack, 2);
   }

   /**
    * Sets the track.
    * <p>
    * @param aTrack The track to set.
    * @param aPadding the number of 0's to pad this track with
    */
   public void setTrack(final String aTrack, final int aPadding) {
      final String current = StringUtils.defaultIfEmpty(aTrack, "0").trim();
      this.track = StringUtils.leftPad(current, aPadding, "0").trim();
      try {
         audioFile.getTag().setTrack(this.track);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: " + ex.getMessage()); //AZ
      }
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setTrackLength(long)
    */
   public void setTrackLength(long aTrackLength) {
      this.trackLength = aTrackLength;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#setYear(java.lang.String)
    */
   public void setYear(String aYear) {
      this.year = StringUtils.defaultIfEmpty(aYear, CURRENT_YEAR).trim();
      try {
         audioFile.getTag().setYear(this.year);
      } catch (Exception ex) {
         LOG.error("FieldDataInvalidException", ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, "FieldDataInvalidException: "+ex.getMessage()); //AZ
      }
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#isVBR()
    */
   public boolean isVBR() {
      return audioFile.isVbr();
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#removeTags()
    */
   public void removeTags() throws MusicTagException {
      if (audioFile != null) {
         try {
            AudioFileIO.delete(audioFile);
         } catch (Exception e) {
            throw new MusicTagException("Error removing AudioFile tag: " + e.getMessage(), e);
         }
         initializeTags();
      }
   }

   /**
    * Renames this Music file based on a format from prefs. The format is in
    * aFormat and can have values %n for track number, %t for title, %a for
    * artist, and %d for disc. Replaces any invalid characters (\\, /, :, , *, ?, ", <, >,
    * or |) with underscores _ to prevent any errors on file systems. Examples:
    * %n -%t = 01 - Track.mp3 %a - %d - %n - %t = Artist - Album - 01 -
    * Track.mp3
    * <p>
    * @param aFormat the string format like %n -%t to rename 01 - Track.mp3
    * @return true if renamed, false if failure
    */
   public boolean renameFile(String aFormat) {
      boolean result = false;
      try {
         final String newFileName = createFilenameFromFormat(aFormat);
         final File newFile = new File(newFileName);
         // close the audioFile
         audioFile = null;

         result = this.file.renameTo(newFile);
         if (result) {
            this.file = newFile;
            this.audioFile = AudioFileIO.read(newFile);
            initializeTags();
         }
      } catch (Exception ex) {
    	 final String errorMessage = ResourceUtils.getString("messages.ErrorRenamingFile");
         LOG.error(errorMessage, ex);
   	     final MainFrame mainFrame = (MainFrame) Application.getDefaultParentFrame();
	     MessageUtil.showError(mainFrame, errorMessage); //AZ
      }
      return result;
   }

   /*
    * (non-Javadoc)
    * @see com.melloware.jukes.file.tag.MusicTag#save()
    */
   public void save() throws MusicTagException {
      if (audioFile != null) {
         try {
            audioFile.commit();
         } catch (CannotWriteException ex) {
            throw new MusicTagException("Error saving AudioFile tag: " + ex.getMessage());
         }

      }

   }

   /**
    * Initialize the tags for this audio file.
    */
   private void initializeTags() {
      // initialize private variables from tags
      this.getDisc();
      this.getArtist();
      this.getComment();
      this.getGenre();
      this.getTitle();
      this.getTrack();
      this.getYear();
      this.getTrackLength();
      this.getEncodedBy();
   }

}