package lumi.sample.action;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Blocked;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;
import lumi.sample.service.SampleService;

/**
 * アップロード処理のAction。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, type="json" , params={"root","result"}),
})
@Controller
@Scope("prototype")
@Log4j2
public class UploadAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("upload")
	public String start() throws Exception {

		boolean bresult = service.upload(uploadfile, uploadfileFileName , uploadfileContentType);

		result = new HashMap<String, Object>();
		result.put("status", bresult);
		if ( bresult ) {
			result.put("title", getText("upload.success.title"));
			List<String> successFileNames = service.getSuccessFileNames();
			displayFilenames = new StringBuilder("");
			for ( String successFileName : successFileNames ) {
				displayFilenames.append(separator).append(successFileName);
			}
			result.put("message", getText("upload.success.message" , Arrays.asList(displayFilenames.substring(separator.length()))) );
		} else {
			result.put("title", getText("upload.failure.title"));
			result.put("message", getText("upload.failure.message" , Arrays.asList(service.getInprogressFileName())) );
		}

		addActionMessage(getText("upload.complete"));

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return SUCCESS;
	}

	/**
	 * Actionクラスから実行する業務ロジックのServiceクラス。Autowiredがついたフィールドが自動的に対象となる。
	 */
	@Blocked
	@Autowired
	@Getter @Setter
	private SampleService service;

	/**
	 * アップロードファイルのFileオブジェクト
	 */
	@Getter @Setter
	private File[] uploadfile;

	/**
	 * アップロードしたファイルのコンテンツタイプ
	 */
	@Getter @Setter
	private String[] uploadfileContentType;

	/**
	 * アップロードしたファイルの元ファイル名
	 */
	@Getter @Setter
	private String[] uploadfileFileName;

	/**
	 * Ajaxレスポンスの文字列
	 */
	@Getter @Setter
	private Map<String, Object> result;

	@Blocked
	private StringBuilder displayFilenames;

	private static final String separator = ",";
}
