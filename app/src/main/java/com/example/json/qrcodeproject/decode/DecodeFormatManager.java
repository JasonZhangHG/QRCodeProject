
package com.example.json.qrcodeproject.decode;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import com.google.zxing.BarcodeFormat;

public class DecodeFormatManager {

	// 1D解码
	static final Set<BarcodeFormat> PRODUCT_FORMATS;
	static final Set<BarcodeFormat> INDUSTRIAL_FORMATS;
	static final Set<BarcodeFormat> ONE_D_FORMATS;

	// 二维码解码
	static final Set<BarcodeFormat> QR_CODE_FORMATS;
	
	static final Collection<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet
			.of(BarcodeFormat.DATA_MATRIX);

	static {
		PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
		INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
		ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS);
		ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS);

		QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
	}

	public static Collection<BarcodeFormat> getQrCodeFormats() {
		return QR_CODE_FORMATS;
	}

	public static Collection<BarcodeFormat> getBarCodeFormats() {
		return ONE_D_FORMATS;
	}
}
