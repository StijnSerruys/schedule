package be.dealloc.schedule.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import be.dealloc.schedule.R;
import be.dealloc.schedule.activities.dispatchers.CalendarNavigationDispatcher;
import be.dealloc.schedule.activities.fragments.ListFragment;
import be.dealloc.schedule.activities.fragments.WeekFragment;
import be.dealloc.schedule.contracts.entities.calendars.Calendar;
import be.dealloc.schedule.contracts.entities.calendars.CalendarManager;
import be.dealloc.schedule.facades.Dialog;
import be.dealloc.schedule.system.Activity;
import be.dealloc.schedule.system.Application;
import be.dealloc.schedule.system.Fragment;
import be.dealloc.schedule.tasks.ProcessCalendarTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import static butterknife.ButterKnife.findById;

public class CalendarActivity extends Activity implements CalendarNavigationDispatcher.DispatcherTarget
{
	@Inject CalendarManager manager;
	@BindView(R.id.calendar_drawer) DrawerLayout drawer;
	private Fragment current = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setLayout(R.layout.activity_calendar);
		Toolbar toolbar = findById(this, R.id.calendar_toolbar);
		setSupportActionBar(toolbar);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		this.drawer.addDrawerListener(toggle);
		toggle.syncState();

		ButterKnife.<NavigationView>findById(this, R.id.calendar_navview).setNavigationItemSelectedListener(new CalendarNavigationDispatcher(this, drawer));

		if (this.current == null)
			this.swap(R.id.calendar_content, new WeekFragment());
	}

	@Override
	public void onBackPressed()
	{
		if (this.drawer.isDrawerOpen(GravityCompat.START))
			this.drawer.closeDrawer(GravityCompat.START);
		else
			super.onBackPressed();
	}

	@OnClick(R.id.calendar_fab)
	public void onFloatingButtonClicked(FloatingActionButton button)
	{
		Calendar calendar = this.manager.getActiveCalendars().get(0);

		Application.provider().calendarProcessor().execute(calendar, new ProcessCalendarTask.ProcessCallback()
		{
			@Override
			public void onProgress(String status)
			{
				Snackbar.make(button, status, Snackbar.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Throwable error)
			{
				Logger.e(error, "Failed to refresh %s", calendar.getSecurityCode());
				Dialog.error(CalendarActivity.this, R.string.generic_web_error);
			}

			@Override
			public void onSucces()
			{
				recreate(); // Restart the acivity. Easiest
			}
		});
	}

	@Override
	public void onCalendarClicked()
	{
		if (!(this.current instanceof WeekFragment))
		{
			this.current = new WeekFragment();
			this.swap(R.id.calendar_content, this.current);
		}
	}

	@Override
	public void onListClicked()
	{
		if (!(this.current instanceof ListFragment))
		{
			this.current = new ListFragment();
			this.swap(R.id.calendar_content, this.current);
		}
	}

	@Override
	public void onSettingsClicked()
	{
		this.navigate(SettingsActivity.class, false);
	}

	@Override
	public void onShareClicked()
	{
		this.navigate(ShareActivity.class, false);
	}
}
