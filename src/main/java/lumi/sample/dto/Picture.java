/**
 *
 */
package lumi.sample.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * pictureテーブルのDTO。
 * @author A-pZ
 *
 */
@Data
public class Picture implements Serializable {
	private long id;
	private String name;
	private Date create_date;
	private long size;
	private byte[] store_file;
}
