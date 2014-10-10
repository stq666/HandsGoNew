package com.soyomaker.handsgonew.server;

import java.io.IOException;
import java.util.ArrayList;

import com.soyomaker.handsgonew.HandsGoApplication;
import com.soyomaker.handsgonew.R;
import com.soyomaker.handsgonew.db.DBService;
import com.soyomaker.handsgonew.manager.ChessManualServerManager;
import com.soyomaker.handsgonew.model.ChessManual;
import com.soyomaker.handsgonew.reader.XGOOReader;
import com.soyomaker.handsgonew.util.AppConstants;

public class XGOOServer implements IChessManualServer {

	private static final int FIRST_PAGE = 1;
	private int mPage = FIRST_PAGE;
	private ArrayList<ChessManual> mChessManuals = new ArrayList<ChessManual>();
	private XGOOReader mXgooReader = new XGOOReader();
	private boolean mIsRefreshing;
	private boolean mIsLoadingMore;

	@Override
	public String getName() {
		return HandsGoApplication.getAppContext().getString(R.string.xgoo_server);
	}

	@Override
	public ArrayList<ChessManual> getChessManuals() {
		return mChessManuals;
	}

	@Override
	public boolean refresh() {
		boolean success = false;
		if (!mIsRefreshing) {
			mIsRefreshing = true;

			try {
				ArrayList<ChessManual> chessManuals = mXgooReader.readChessManuals(
						HandsGoApplication.getAppContext(), FIRST_PAGE);
				if (!chessManuals.isEmpty()) {
					mPage = FIRST_PAGE;
					mChessManuals.clear();
					mChessManuals.addAll(chessManuals);
					success = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			mIsRefreshing = false;
			return success;
		}
		return success;
	}

	@Override
	public boolean loadMore() {
		boolean success = false;
		if (!mIsLoadingMore) {
			mIsLoadingMore = true;

			try {
				ArrayList<ChessManual> chessManuals = mXgooReader.readChessManuals(
						HandsGoApplication.getAppContext(), mPage + 1);
				if (!chessManuals.isEmpty()) {
					mPage++;// 读取成功之后再+1
					mChessManuals.addAll(chessManuals);
					success = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			mIsLoadingMore = false;
			return success;
		}
		return success;
	}

	@Override
	public boolean isRefreshing() {
		return mIsRefreshing;
	}

	@Override
	public boolean isLoadingMore() {
		return mIsLoadingMore;
	}

	@Override
	public int getTag() {
		return AppConstants.XGOO;
	}

	@Override
	public boolean canRefresh() {
		return true;
	}

	@Override
	public boolean canLoadMore() {
		return true;
	}

	@Override
	public boolean canDelete() {
		return false;
	}

	@Override
	public boolean delete(ChessManual chessManual) {
		return false;
	}

	@Override
	public boolean canCollect() {
		return true;
	}

	@Override
	public boolean collect(ChessManual chessManual) {
		ArrayList<ChessManual> chessManuals = ChessManualServerManager.getInstance()
				.getCollectServer().getChessManuals();
		if (!chessManuals.contains(chessManual)) {
			DBService.saveFavoriteChessManual(chessManual);
			chessManuals.add(chessManual);
		}
		return true;
	}
}