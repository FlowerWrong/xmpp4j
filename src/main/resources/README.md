## Ejabberd module

#### Usage

```ruby
1. 放在ejabberd源码目录src下面
2. make
make

3. copy
sudo mv ebin/module_name.beam /lib/ejabberd/ebin/

4. config ejabberd.yml
modules:
  mod_hello_world: {}

5. restart ejabberd
```