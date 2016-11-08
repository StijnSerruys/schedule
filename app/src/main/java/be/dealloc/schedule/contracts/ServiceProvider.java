package be.dealloc.schedule.contracts;
// Created by dealloc. All rights reserved.

import be.dealloc.schedule.activities.MainActivity;
import be.dealloc.schedule.providers.SystemProvider;
import dagger.Component;

@Component(modules = {
		SystemProvider.class,
})
public interface ServiceProvider
{
	void inject(MainActivity activity);
}
