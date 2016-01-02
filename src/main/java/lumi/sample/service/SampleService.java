package lumi.sample.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lumi.dao.DAO;
import lumi.sample.dto.Picture;
import lumi.sample.dto.SampleDTO;
import lumi.service.LumiService;

/**
 * Serviceクラスのサンプル。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 *
 */
@Scope("prototype")
@Service
@Log4j2
@Transactional(
	    propagation = Propagation.REQUIRED,
	    isolation = Isolation.DEFAULT,
	    readOnly = false,
	    rollbackFor = { RuntimeException.class, Exception.class })
public class SampleService extends LumiService {

	/**
	 * サンプルのServiceクラス。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> execute(String param) throws Exception {

		String mail = sample.getMail();
		log.debug(mail);

		addInfoMessage("display.complete");

		// 検索結果を返す。
		return null;
	}

	/**
	 * アップロードファイルをテーブルpictureに保管する。
	 * 保管の成否を返す。
	 * @param uploadfile Struts2が受け取ったアップロードファイルの中間ファイルFile
	 * @param uploadfileFileName アップロードしたファイル名(ブラウザから送信)
	 * @param uploadfileContentType アップロードファイルのコンテンツタイプ
	 * @return 保管の成否
	 * @throws Exception 発生した例外。
	 */
	public boolean upload(File[] uploadfile , String[] uploadfileFileName , String[] uploadfileContentType) throws Exception {

		int length = uploadfile.length;
		boolean result = false;
		for ( int i=0;i<length;i++) {

			if ( log.isDebugEnabled()) {
				log.debug("size :{}" , uploadfile[i].length());
				log.debug("name :{}" , uploadfileFileName[i]);
				log.debug("content-type : {}" , uploadfileContentType[i]);

			}

			//image/jpeg , image/png以外は受け取らない
			if (! constService.getAvailableContentTypes().contains(uploadfileContentType[i])) {
				log.warn("Not available contentType:{}" ,uploadfileContentType[i] );
				return false;
			}
			result = storeFile(uploadfile[i] , uploadfileFileName[i]);
			if (! result ) return false;
		}
		return result;
	}


	/**
	 * データベースにアップロードファイルを格納する。
	 * @param uploadfile アップロード中間ファイルのFile
	 * @param uploadfileFileName ファイル名
	 * @return 保管結果
	 * @throws Exception
	 */
	boolean storeFile(@NonNull File uploadfile , @NonNull String uploadfileFileName) throws Exception {
		Picture pic = new Picture();
		pic.setName(uploadfileFileName);
		pic.setSize(uploadfile.length());
		pic.setStore_file(FileUtils.readFileToByteArray(uploadfile));
		pic.setCreate_date(new Date());

		int count = 0;

		try {
			count = dao.insert(Query.storeFile.name(), pic);
		} catch ( Exception e) {
			log.warn("file store failure." , e);
		}

		return ( count == 1);
	}

	/**
	 * DAOの指定。Mybatisを利用してデータベースアクセスを実行する。
	 */
	@Autowired
	private DAO dao;

	@Autowired
	private ConstanceService constService;

	/**
	 * Mybatisで定義するSQLのSQL-ID。
	 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
	 *
	 */
	public enum Query {
		storeFile ,
	}

	@Autowired
	private SampleDTO sample;
}
