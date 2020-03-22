## Dogfight Client

![Plane](pictures/plane4.gif)
![Plane](pictures/plane5.gif)
![Plane](pictures/plane6.gif)
![Plane](pictures/plane7.gif)
![Plane](pictures/plane8.gif)
![Plane](pictures/plane9.gif)

![Plane](pictures/runway.gif)

> The battle for the air begins! Germany vs. Great Britain. Choose your side and fly your planes to victory as you re-create the aerial battles of the First World War!.
> 
> Take off in a plane of your choice and dominate the skies!
---
This is the decompiled client side Java source code and assets to the classic Dogfight game featured on [Playforia](http://www.playforia.net/play/dogfight/).
(formerly Playray).
Dogfight was (still is?) a large multiplayer [PVP aerial combat game](https://www.youtube.com/watch?v=qCCCUXUwlT8) introduced circa 2006. 

EDIT: As of January 7, 2019, Playforia/Aapeli has announced the closure of their website.

A modern remake of this game is in progress and can be found at:
https://github.com/mattbruv/Lentokonepeli

### Motivation
The purpose of this repository is to save what little information we can about this great game before it inevitably fades into dust over time.

The original game was designed to be deeply interconnected with Plaray, using its account and coin system.
If a user wanted to play the game, they needed to join a lobby.
However, custom user-created lobbies needed to be bought with real money and expired after a short few days.

Naturally, this leads to very few lobbies since people did not want to pay real money to play in a private or custom server for such a simple game.
The site claims that there are always 2 free lobbies, however at the time of collecting this information there are 0 lobbies available (free or paid), and a grand total of 0 players. It is sad to see, and has likely been this way for nearly a decade now. 

### Audio Conversion
The original audio files are in [`.au` file format](https://en.wikipedia.org/wiki/Au_file_format), which is very outdated.

Using `ffmpeg`, I have converted these files into `.mp3` and `.wav` for preservation sake.

MP3:
`ffmpeg -i audio.au -acodec libmp3lame -qscale:a 0 audio.mp3`

WAV:
`ffmpeg -i audio.au audio.wav`


### What about copyright?
This is only the client side of the game which is unplayable without the server side code.
All of the source code and assets found in this repository are automatically downloaded by your computer every time you visit the game, so nothing about this is private to begin with.

All that was done here was a simple decompile of the `Dogfight.jar` file found here:

`http://game21.playforia.net/Dogfight/Dogfight.jar` (Offline as of 2019-01-07)

### Compiling

This will not compile in its current state.
You will need to download `Shared.jar` which is a helper library shared among other Playray games. 
It can be found here:

`http://game21.playforia.net/Shared/Shared.jar` (Offline as of 2019-01-07)

After that, there will likely be a handfull of decompiler errors that need to be resolved.
I haven't cared enough to do this, so please submit a PR if you decide to get this working.