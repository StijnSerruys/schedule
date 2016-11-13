package be.dealloc.schedule.facades;
// Created by dealloc. All rights reserved.

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import be.dealloc.schedule.R;

/**
 * Dialog class.
 *
 * @author dealloc
 */
public final class Dialog
{
	private Dialog()
	{
	}

	public static AlertDialog confirm(Context context, final int title, final int content, DialogInterface.OnClickListener onConfirm, DialogInterface.OnClickListener onDeny)
	{
		return (new AlertDialog.Builder(context))
				.setTitle(title)
				.setMessage(content)
				.setIcon(R.mipmap.ic_launcher)
				.setPositiveButton(android.R.string.yes, onConfirm)
				.setNegativeButton(android.R.string.no, onDeny)
				.setCancelable(false)
				.create();
	}

	public static AlertDialog msgbox(Context context, final int title, final int content, DialogInterface.OnClickListener onClick)
	{
		return (new AlertDialog.Builder(context))
				.setTitle(title)
				.setIcon(R.mipmap.ic_launcher)
				.setMessage(content)
				.setPositiveButton(android.R.string.ok, onClick)
				.create();
	}

	public static AlertDialog msgbox(Context context, final int title, final int content)
	{
		return msgbox(context, title, content, null);
	}

	public static AlertDialog input(Context context, final int title, final int content, final TextDialogCallback onConfirm, DialogInterface.OnClickListener onDeny)
	{
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_TEXT);

		DialogInterface.OnClickListener confirm = (onConfirm == null ? null : new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int i)
			{
				onConfirm.confirm(dialog, input.getText().toString());
			}
		});

		return (new AlertDialog.Builder(context))
				.setIcon(R.mipmap.ic_launcher)
				.setTitle(title)
				.setMessage(content)
				.setView(input)
				.setPositiveButton(android.R.string.yes, confirm)
				.setNegativeButton(android.R.string.no, onDeny)
				.setCancelable(false)
				.create();
	}

	public static AlertDialog msgbox(Context context, String message)
	{
		return (new AlertDialog.Builder(context))
				.setTitle(R.string.app_name)
				.setIcon(R.mipmap.ic_launcher)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, null)
				.create();
	}

	public static AlertDialog error(Context context, final int error_msg)
	{
		return (new AlertDialog.Builder(context))
				.setTitle(R.string.app_name)
				.setIcon(R.mipmap.ic_error)
				.setMessage(error_msg)
				.setPositiveButton(android.R.string.ok, null)
				.create();
	}

	public static AlertDialog warning(Context context, final int error_msg)
	{
		return (new AlertDialog.Builder(context))
				.setTitle(R.string.app_name)
				.setIcon(R.mipmap.ic_warning)
				.setMessage(error_msg)
				.setPositiveButton(android.R.string.ok, null)
				.create();
	}

	public interface TextDialogCallback
	{
		void confirm(DialogInterface dialog, String content);
	}
}
