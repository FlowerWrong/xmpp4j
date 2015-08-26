%%%-------------------------------------------------------------------
%%% @author yy
%%% @copyright (C) 2015, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 26. 八月 2015 下午3:30
%%%-------------------------------------------------------------------
-module(mod_hello_world).
-author("yy").

-behaviour(gen_mod).

%% Required by ?INFO_MSG macros
-include("logger.hrl").

%% API
%% gen_mod API callbacks
-export([start/2, stop/1]).

start(_Host, _Opts) ->
  ?INFO_MSG("Hello, ejabberd world!", []),
  ok.

stop(_Host) ->
  ?INFO_MSG("Bye bye, ejabberd world!", []),
  ok.
