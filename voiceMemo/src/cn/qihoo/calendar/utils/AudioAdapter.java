package cn.qihoo.calendar.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.qihoo.calendar.bean.Audio;
import cn.qihoo.calender.sqlite.AudioHelper;

import com.example.voicememo.QueryActivity;
import com.example.voicememo.R;

public class AudioAdapter extends BaseAdapter {
	private LayoutInflater inflater;// 这个一定要懂它的用法及作用
	private Context context;
	Cursor cur;
	List<Audio> list = new ArrayList<Audio>();

	// 构造函数:要理解(这里构造方法的意义非常强大,你也可以传一个数据集合的参数,可以根据需要来传参数)
	public AudioAdapter(Context context, Cursor cur) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.cur = cur;

		while (cur.moveToNext()) {

			int nameColumnIndex = cur.getColumnIndex("date");
			String date = cur.getString(nameColumnIndex);

			int urlColumnIndex = cur.getColumnIndex("url");
			String url = cur.getString(urlColumnIndex);

			int idColumnIndex = cur.getColumnIndex("_id");
			int id = cur.getInt(idColumnIndex);

			Audio audio = new Audio();
			audio.setUrl(url);
			audio.setDate(date);
			audio.setId(id);

			list.add(audio);
		}

	}

	// 这里的getCount方法是程序在加载显示到ui上时就要先读取的，这里获得的值决定了listview显示多少行
	@Override
	public int getCount() {

		// 在实际应用中，此处的返回值是由从数据库中查询出来的数据的总条数
		return list.size();
	}

	// 根据ListView所在位置返回View
	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	// 根据ListView位置得到数据源集合中的Id
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 在外面先定义，ViewHolder静态类
	static class ViewHolder {
		// public ImageView img;
		public TextView dateview;
		public Button buttonplay;
		public Button buttondel;
	}

	// 重写adapter最重要的就是重写此方法，此方法也是决定listview界面的样式的
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 有很多例子中都用到这个holder,理解下??
		ViewHolder holder = null;
		// 思考这里为何要判断convertView是否为空 ？？
		if (convertView == null) {
			holder = new ViewHolder();

			// 把vlist layout转换成View【LayoutInflater的作用】
			convertView = inflater.inflate(R.layout.row, null);
			// 通过上面layout得到的view来获取里面的具体控件
			holder.buttonplay = (Button) convertView
					.findViewById(R.id.Button_play);

			holder.buttondel = (Button) convertView
					.findViewById(R.id.Button_del);

			holder.dateview = (TextView) convertView.findViewById(R.id.text0);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.dateview.setText(list.get(position).getDate());
		// 为listview上的button添加click监听
		holder.buttonplay.setOnClickListener(new View.OnClickListener() {
			boolean mStartPlaying = true;

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "hahahh", 1).show();
				QueryActivity.mFileName = list.get(position).getUrl();

				QueryActivity.onPlay(mStartPlaying);
				if (mStartPlaying) {
					((Button) v).setText("Stop playing");
				} else {
					((Button) v).setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;

			}
		});

		// del
		holder.buttondel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(context, "hahahh", 1).show();

				final AudioHelper helpter = new AudioHelper(context);
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setMessage("真的要删除该记录吗？")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										helpter.del(list.get(position).getId());
										Cursor cur = helpter
												.query4Month("2014-07");

										AudioAdapter adapter = new AudioAdapter(
												context, cur);
										adapter.notifyDataSetChanged();

									}
								})
						.setNegativeButton("否",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});
				AlertDialog ad = builder.create();
				ad.show();
			}

		});

		return convertView;
	}
}
