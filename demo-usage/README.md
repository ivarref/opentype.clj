# demo-usage

A project to demonstrate usage of `opentype.clj`.

## Usage

```clojure
=> (use 'opentype-clj.core)
    
=> (load-font "fonts/Roboto-Regular.ttf")
{:name "Roboto Regular", :unitsPerEm 2048, :ascender 1900, :descender -500, :font-obj #object[opentype_clj.core$load_font$fn__15357$fn__15358 0x2db0fd7e "opentype_clj.core$load_font$fn__15357$fn__15358@2db0fd7e"]}
```
    
    

## License

Copyright © 2018 Ivar Refsdal

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
